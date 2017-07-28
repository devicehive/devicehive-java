package com.devicehive.websocket.api.impl;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.api.listener.LoginListener;
import com.devicehive.websocket.model.repsonse.ErrorAction;
import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.AuthenticateAction;
import com.devicehive.websocket.model.request.TokenAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import static com.devicehive.websocket.model.request.AuthenticateAction.AUTH;
import static com.devicehive.websocket.model.request.TokenAction.TOKEN;

public class AuthWSImpl extends WebSocketListener implements AuthApi {

    private WebSocket ws;
    private Gson writer = new Gson();
    private LoginListener loginListener;

    public AuthWSImpl(OkHttpClient client, Request request, LoginListener loginListener) {
        ws = client.newWebSocket(request, this);
        this.loginListener = loginListener;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory()).create();

        ResponseAction action = gson.fromJson(text, ResponseAction.class);
        String actionName = action.getAction();
        String status = action.getStatus();

        System.out.println(text);
        if (status.equalsIgnoreCase(ErrorAction.ERROR)) {
            ErrorAction errorAction = gson.fromJson(text, ErrorAction.class);
            loginListener.onError(errorAction);
        } else if (actionName.equalsIgnoreCase(TOKEN)) {
            JwtTokenResponse tokenVO = gson.fromJson(text, JwtTokenResponse.class);
            authenticate(tokenVO.getAccessToken());
            loginListener.onResponse(tokenVO);
        } else if (actionName.equalsIgnoreCase(AUTH)) {
            loginListener.onAuthenticate(action);
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println(t.getMessage());
    }

    @Override
    public void getToken(String login, String password) {
        if (ws == null) {
            return;
        }
        TokenAction tokenAction = new TokenAction();
        tokenAction.setLogin(login);
        tokenAction.setPassword(password);
        String json = writer.toJson(tokenAction);
        ws.send(json);
    }

    @Override
    public void authenticate(String token) {
        if (ws == null) {
            return;
        }
        AuthenticateAction authAction = new AuthenticateAction();
        authAction.setToken(token);
        ws.send(writer.toJson(authAction));
    }

}
