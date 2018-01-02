/*
 *
 *
 *   WebSocketClient.java
 *
 *   Copyright (C) 2018 DataArt
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

package com.github.devicehive.websocket.api;

import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.request.AuthenticateAction;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

import static com.github.devicehive.websocket.model.ActionConstant.AUTHENTICATE;
import static com.github.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

public class WebSocketClient extends WebSocketListener implements WebSocketCreator, Closeable {

    private Request request;
    private OkHttpClient client;
    private WebSocket ws;
    private Map<String, BaseWebSocketApi> map = new HashMap<>();
    private Gson gson = new Gson();
    private AuthListener authListener;

    WebSocketClient(Builder builder) {
        this.client = new OkHttpClient();
        this.request = builder.request;
        this.ws = client.newWebSocket(request, this);

    }

    public static class Builder {
        private Request request;

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.request = new Request.Builder().url(url).build();
            return this;
        }

        public WebSocketClient build() {
            return new WebSocketClient(this);
        }

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ResponseAction action = getResponseAction(text);
        String actionName = action.getAction();
        if (authListener != null && actionName.startsWith(AUTHENTICATE)) {
            if (action.getStatus().equals(SUCCESS)) {
                authListener.onSuccess(action);
            } else {
                authListener.onError(gson.fromJson(text, ErrorResponse.class));
            }
        }
        BaseWebSocketApi api = getBaseWebSocketApi(actionName);
        if (api != null) {
            api.onMessage(text);
        }
    }

    public void setListener(AuthListener authListener) {
        this.authListener = authListener;
    }

    private ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }


    private BaseWebSocketApi getBaseWebSocketApi(String actionName) {
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
    public void close() {
        client.dispatcher().executorService().shutdown();
        map.clear();
        ws.close(1000, null);
    }

    WebSocket getWebSocket() {
        return ws;
    }

    public void authenticate(String accessToken) {
        AuthenticateAction authAction = new AuthenticateAction();
        authAction.setToken(accessToken);
        ws.send(gson.toJson(authAction));
    }

    @Override
    public DeviceWS createDeviceWS() {
        DeviceWS deviceWS = new DeviceWS(this);
        put(DeviceWS.TAG, deviceWS);
        return deviceWS;
    }

    @Override
    public CommandWS createCommandWS() {
        CommandWS commandWS = new CommandWS(this);
        put(CommandWS.TAG, commandWS);
        return commandWS;
    }

    @Override
    public ConfigurationWS createConfigurationWS() {
        ConfigurationWS configurationWS = new ConfigurationWS(this);
        put(ConfigurationWS.TAG, configurationWS);
        return configurationWS;
    }

    @Override
    public NotificationWS createNotificationWS() {
        NotificationWS notificationWS = new NotificationWS(this);
        put(NotificationWS.TAG, notificationWS);
        return notificationWS;
    }

    @Override
    public NetworkWS createNetworkWS() {
        NetworkWS networkWS = new NetworkWS(this);
        put(NetworkWS.TAG, networkWS);
        return networkWS;
    }

    @Override
    public TokenWS createTokenWS() {
        TokenWS tokenWS = new TokenWS(this);
        put(TokenWS.TAG, tokenWS);
        return tokenWS;
    }

    @Override
    public UserWS createUserWS() {
        UserWS userWS = new UserWS(this);
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
