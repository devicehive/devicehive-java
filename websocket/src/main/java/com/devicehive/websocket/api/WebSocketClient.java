/*
 *
 *
 *   WebSocketClient.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.*;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import com.devicehive.websocket.model.request.AuthenticateAction;
import com.devicehive.websocket.model.request.TokenRefreshAction;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.devicehive.websocket.model.ActionConstant.AUTHENTICATE;
import static com.devicehive.websocket.model.ActionConstant.TOKEN_REFRESH;
import static com.devicehive.websocket.model.repsonse.ErrorResponse.ERROR;
import static com.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

public class WebSocketClient extends WebSocketListener implements WebSocketCreator, Closeable {

    private Request request;
    private OkHttpClient client;
    private String refreshToken;
    private WebSocket ws;
    private Map<String, BaseWebSocketApi> map = new HashMap<>();
    private Gson gson = new Gson();

    WebSocketClient(Builder builder) {
        this.client = new OkHttpClient();
        this.request = builder.request;
        this.ws = client.newWebSocket(request, this);

    }

    public static class Builder {
        private Request request;
        private String accessToken = null;
        private String refreshToken = null;

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.request = new Request.Builder().url(url).build();
            return this;
        }

        public Builder token( String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken( String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public WebSocketClient build() {
            WebSocketClient webSocketClient = new WebSocketClient(this);
            webSocketClient.setRefreshToken(refreshToken);
            if (accessToken != null) {
                webSocketClient.authenticate(accessToken);
            }
            return webSocketClient;
        }

    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ResponseAction action = getResponseAction(text);
        String actionName = action.getAction();
        if (actionName.startsWith(TOKEN_REFRESH) && action.getStatus().equalsIgnoreCase(SUCCESS)) {
            authenticate(((TokenRefreshResponse) action).getAccessToken());

        } else if (actionName.startsWith(AUTHENTICATE) && action.getStatus().equalsIgnoreCase(ERROR)) {
            for (BaseWebSocketApi a : map.values()) {
                a.onMessage(text);
            }
            TokenRefreshAction refreshAction = new TokenRefreshAction();
            refreshAction.setRefreshToken(refreshToken);
            ws.send(gson.toJson(refreshAction));
            return;
        }
        BaseWebSocketApi api = getBaseWebSocketApi(actionName);
        if (api != null) {
            api.onMessage(text);
        }

    }

    private ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }


    private BaseWebSocketApi getBaseWebSocketApi( String actionName) {
        BaseWebSocketApi api = null;
        if (actionName.startsWith(DeviceWS.TAG)) {
            api = map.get(DeviceWS.TAG);
        } else if (actionName.startsWith(CommandWS.TAG)) {
            api = map.get(CommandWS.TAG);
        } else if (actionName.startsWith(ConfigurationWS.TAG)) {
            api = map.get(ConfigurationWS.TAG);
        } else if (actionName.startsWith(NotificationWS.TAG)) {
            api = map.get(NotificationWS.TAG);
        } else if (actionName.startsWith(NetworkWS.TAG)) {
            api = map.get(NetworkWS.TAG);
        } else if (actionName.startsWith(UserWS.TAG)) {
            api = map.get(UserWS.TAG);
        } else if (actionName.startsWith(TokenWS.TAG)) {
            api = map.get(TokenWS.TAG);
        }
        return api;
    }

    @Override
    public void close() throws IOException {
        client.dispatcher().executorService().shutdown();
        map.clear();
        ws.close(1000, null);
    }

    public void authenticate(String token) {
        AuthenticateAction authAction = new AuthenticateAction();
        authAction.setToken(token);
        ws.send(gson.toJson(authAction));
    }

    @Override
    public DeviceWS createDeviceWS(DeviceListener listener) {
        DeviceWS deviceWS = new DeviceWS(ws, listener);
        put(DeviceWS.TAG, deviceWS);
        return deviceWS;
    }

    @Override
    public CommandWS createCommandWS(CommandListener listener) {
        CommandWS commandWS = new CommandWS(ws, listener);
        put(CommandWS.TAG, commandWS);
        return commandWS;
    }

    @Override
    public ConfigurationWS createConfigurationWS(ConfigurationListener listener) {
        ConfigurationWS configurationWS = new ConfigurationWS(ws, listener);
        put(ConfigurationWS.TAG, configurationWS);
        return configurationWS;
    }

    @Override
    public NotificationWS createNotificationWS(NotificationListener listener) {
        NotificationWS notificationWS = new NotificationWS(ws, listener);
        put(NotificationWS.TAG, notificationWS);
        return notificationWS;
    }

    @Override
    public NetworkWS createNetworkWS(NetworkListener listener) {
        NetworkWS networkWS = new NetworkWS(ws, listener);
        put(NetworkWS.TAG, networkWS);
        return networkWS;
    }

    @Override
    public TokenWS createTokenWS(TokenListener listener) {
        TokenWS tokenWS = new TokenWS(ws, listener);
        put(TokenWS.TAG, tokenWS);
        return tokenWS;
    }

    @Override
    public UserWS createUserWS(UserListener listener) {
        UserWS userWS = new UserWS(ws, listener);
        put(UserWS.TAG, userWS);
        return userWS;
    }


    private void put(String key, BaseWebSocketApi listener) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
        map.put(key, listener);
    }

}
