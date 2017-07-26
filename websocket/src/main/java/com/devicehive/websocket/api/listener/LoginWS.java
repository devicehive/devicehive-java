package com.devicehive.websocket.api.listener;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

public class LoginWS extends WebSocketListener {

    public static final String TOKEN = "token";
    private WebSocket ws;
    private Gson writer = new Gson();
    private LoginListener loginListener;

    public LoginWS(OkHttpClient client, Request request, LoginListener loginListener) {
        ws = client.newWebSocket(request, this);
        this.loginListener = loginListener;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory()).create();

        Action action = gson.fromJson(text, Action.class);
        String actionName = action.getAction();
        String status = action.getStatus();
        if (status.equalsIgnoreCase("error")) {
            ErrorAction errorAction = gson.fromJson(text, ErrorAction.class);
            loginListener.onError(errorAction);
            return;
        }

        if (action.getAction().equalsIgnoreCase(TOKEN)) {
            JwtTokenVO tokenVO = gson.fromJson(text, JwtTokenVO.class);
            loginListener.onResponse(tokenVO);
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println(t.getMessage());
    }

    public void authenticate(String login, String password) {
        if (ws == null) {
            return;
        }
        TokenAction tokenAction = new TokenAction();
        tokenAction.setLogin(login);
        tokenAction.setPassword(password);
        String json = writer.toJson(tokenAction);
        ws.send(json);
    }

    public void authenticate(String token) {
        if (ws == null) {
            return;
        }
        AuthAction authAction = new AuthAction();
        authAction.setAction("authenticate");
        authAction.setToken(token);
        ws.send(writer.toJson(authAction));
    }

}
