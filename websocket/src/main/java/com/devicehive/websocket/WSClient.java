package com.devicehive.websocket;

import com.devicehive.websocket.api.*;
import com.devicehive.websocket.listener.*;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.AuthenticateAction;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WSClient extends WebSocketListener implements Closeable {

    private Request request;
    private OkHttpClient client;
    private WebSocket ws;
    private Map<String, BaseWebSocketApi> map = new HashMap<>();
    private Gson gson = new Gson();

    WSClient(Builder builder) {
        this.client = new OkHttpClient();
        this.request = builder.request;
        this.ws = client.newWebSocket(request, this);

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ResponseAction action = getResponseAction(text);
        BaseWebSocketApi listener = null;
        String actionName = action.getAction();

        if (actionName.startsWith(DeviceWS.TAG)) {
            listener = map.get(DeviceWS.TAG);
        } else if (actionName.startsWith(CommandWS.TAG)) {
            listener = map.get(CommandWS.TAG);
        } else if (actionName.startsWith(ConfigurationWS.TAG)) {
            listener = map.get(ConfigurationWS.TAG);
        } else if (actionName.startsWith(NotificationWS.TAG)) {
            listener = map.get(NotificationWS.TAG);
        } else if (actionName.startsWith(NetworkWS.TAG)) {
            listener = map.get(NetworkWS.TAG);
        } else if (actionName.startsWith(UserWS.TAG)) {
            listener = map.get(UserWS.TAG);
        } else if (actionName.startsWith(TokenWS.TAG)) {
            listener = map.get(TokenWS.TAG);
        }

        if (listener != null) {
            listener.onMessage(text);
        }


    }

    private ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }

    @Override
    public void close() throws IOException {
        client.dispatcher().executorService().shutdown();
        map.clear();
        ws.close(1000, null);
    }

    public static class Builder {
        private Request request;
        private String token = null;

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.request = new Request.Builder().url(url).build();
            return this;
        }

        public Builder token(@Nonnull String token) {
            this.token = token;
            return this;
        }

        public WSClient build() {
            WSClient wsClient = new WSClient(this);
            if (token != null) {
                wsClient.authenticate(token);
            }
            return wsClient;
        }

    }

    public void authenticate(String token) {
        AuthenticateAction authAction = new AuthenticateAction();
        authAction.setToken(token);
        ws.send(gson.toJson(authAction));
    }

    //TODO add factory

    public DeviceWS createDeviceWS(DeviceListener listener) {
        DeviceWS deviceWS = new DeviceWS(ws, listener);
        put(DeviceWS.TAG, deviceWS);
        return deviceWS;
    }

    public CommandWS createCommandWS(CommandListener listener) {
        CommandWS commandWS = new CommandWS(ws, listener);
        put(CommandWS.TAG, commandWS);
        return commandWS;
    }

    public ConfigurationWS createConfigurationWS(ConfigurationListener listener) {
        ConfigurationWS configurationWS = new ConfigurationWS(ws, listener);
        put(ConfigurationWS.TAG, configurationWS);
        return configurationWS;
    }

    public NotificationWS createNotificationWS(NotificationListener listener) {
        NotificationWS notificationWS = new NotificationWS(ws, listener);
        put(NotificationWS.TAG, notificationWS);
        return notificationWS;
    }

    public NetworkWS createNetworkWS(NetworkListener listener) {
        NetworkWS networkWS = new NetworkWS(ws, listener);
        put(NetworkWS.TAG, networkWS);
        return networkWS;
    }

    public TokenWS createTokenWS(TokenListener listener) {
        TokenWS tokenWS = new TokenWS(ws, listener);
        put(TokenWS.TAG, tokenWS);
        return tokenWS;
    }

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
