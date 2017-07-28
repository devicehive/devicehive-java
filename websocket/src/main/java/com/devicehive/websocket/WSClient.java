package com.devicehive.websocket;

import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.listener.LoginListener;
import com.devicehive.websocket.api.AuthWS;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WSClient {

    private Request request;
    private OkHttpClient client;

    WSClient(Builder builder) {
        this.client = new OkHttpClient();
        this.request = builder.request;

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
        return new AuthWS(client, request, loginListener);
    }

    public DeviceWS createDeviceWS(DeviceListener deviceListener) {
        return new DeviceWS(client, request, deviceListener);
    }

}
