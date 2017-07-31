package com.devicehive.websocket;

import com.devicehive.websocket.api.AuthWS;
import com.devicehive.websocket.api.BaseWebSocketListener;
import com.devicehive.websocket.api.CommandWS;
import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.listener.LoginListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WSClient implements Closeable {

    private Request request;
    private OkHttpClient client;
    private Map<String, BaseWebSocketListener> map = new HashMap<>();

    WSClient(Builder builder) {
        this.client = new OkHttpClient();
        this.request = builder.request;

    }

    @Override
    public void close() throws IOException {
        client.dispatcher().executorService().shutdown();
        for (BaseWebSocketListener l : map.values()) {
            l.close();
            map.remove(l.getKey());
        }
    }

    public static class Builder {
        private Request request;

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.request = new Request.Builder().url(url).build();
            return this;
        }

        public WSClient build() {
            return new WSClient(this);
        }

    }

    public AuthWS createLoginWS(LoginListener loginListener) {
        AuthWS authWS = new AuthWS(client, request, loginListener);
        put(AuthWS.TAG, authWS);
        return authWS;
    }

    public DeviceWS createDeviceWS(DeviceListener deviceListener) {
        DeviceWS deviceWS = new DeviceWS(client, request, deviceListener);
        put(DeviceWS.TAG, deviceWS);
        return deviceWS;
    }

    public CommandWS createCommandWS(CommandListener deviceListener) {
        CommandWS commandWS = new CommandWS(client, request, deviceListener);
        put(CommandWS.TAG, commandWS);
        return commandWS;
    }

    private void put(String key, BaseWebSocketListener listener) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
        map.put(key, listener);
    }

}
