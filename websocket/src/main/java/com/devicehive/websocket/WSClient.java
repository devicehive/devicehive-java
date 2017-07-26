package com.devicehive.websocket;

import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.api.listener.LoginListener;
import com.devicehive.websocket.api.listener.LoginWS;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocketListener;

import java.util.HashMap;
import java.util.Map;

public class WSClient {

    private Request request;
    private OkHttpClient client;
    private final String url;

    private Map<String, WebSocketListener> map = new HashMap<>();


    public WSClient(String url) {
        this.url = url;
        client = new OkHttpClient();
        request = new Request.Builder().url(url).build();
    }

    public void setAuth(String username, String password) {

    }

    public LoginWS addLoginListener(LoginListener loginListener) {
        LoginWS loginWS = new LoginWS(client, request, loginListener);
        map.put("auth", loginWS);
        return loginWS;
    }

    public DeviceWS addDeviceListener(DeviceListener deviceListener) {
        DeviceWS deviceWS = new DeviceWS(client, request, deviceListener);
        map.put("device", deviceWS);
        return deviceWS;
    }

    public void authenticate(String login, String password) {
        LoginWS loginWS = (LoginWS) map.get("auth");
        loginWS.authenticate(login,password);
    }

}
