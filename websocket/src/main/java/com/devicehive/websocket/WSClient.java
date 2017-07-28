package com.devicehive.websocket;

import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.api.listener.LoginListener;
import com.devicehive.websocket.api.listener.LoginWS;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WSClient {

    private Request request;
    private OkHttpClient client;

    private String login;
    private String password;
    private String token;


    WSClient(Builder builder) {
        this.client = new OkHttpClient();
        this.request = builder.request;
        this.login = builder.login;
        this.password = builder.password;
        this.token = builder.token;

    }

    public static class Builder {
        private String login;
        private String password;
        private String token;
        private Request request;

        public Builder authParams(String login, String password) {
            if (login == null) throw new NullPointerException("login == null");
            if (password == null) throw new NullPointerException("password == null");
            this.login = login;
            this.password = password;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.request = new Request.Builder().url(url).build();
            return this;
        }

        public WSClient build() {
            return new WSClient(this);
        }

    }

    public void addLoginListener(LoginListener loginListener) {
        LoginWS loginWS = new LoginWS(client, request, loginListener);
        if (token != null && token.length() > 0) {
            loginWS.authenticate(token);
        } else {
            loginWS.getToken(login, password);
        }
    }

    public DeviceWS addDeviceListener(DeviceListener deviceListener) {
        return new DeviceWS(client, request, deviceListener);
    }

}
