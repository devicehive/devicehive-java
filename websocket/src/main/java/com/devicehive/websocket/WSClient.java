package com.devicehive.websocket;

import com.devicehive.websocket.api.DeviceWSImpl;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.api.listener.LoginListener;
import com.devicehive.websocket.api.listener.LoginWSImpl;
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

    public LoginWSImpl createLoginWS(LoginListener loginListener) {
        return new LoginWSImpl(client, request, loginListener);
    }

    public DeviceWSImpl createDeviceWS(DeviceListener deviceListener) {
        return new DeviceWSImpl(client, request, deviceListener);
    }

}
