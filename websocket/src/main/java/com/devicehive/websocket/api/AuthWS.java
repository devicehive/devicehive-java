package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.LoginListener;
import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.AuthenticateAction;
import com.devicehive.websocket.model.request.TokenAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

import static com.devicehive.websocket.model.request.AuthenticateAction.AUTH;
import static com.devicehive.websocket.model.request.TokenAction.TOKEN;

public class AuthWS extends BaseWebSocketListener implements AuthApi {

    private LoginListener loginListener;

    public AuthWS(OkHttpClient client, Request request, LoginListener listener) {
        super(client, request, listener);
        this.loginListener = listener;
    }


    @Override
    public void onSuccess(String message) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory()).create();

        ResponseAction action = getResponseAction(message);
        String actionName = action.getAction();
        String status = action.getStatus();

        System.out.println(message);
        if (actionName.equalsIgnoreCase(TOKEN)) {
            JwtTokenResponse tokenVO = gson.fromJson(message, JwtTokenResponse.class);
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
        TokenAction tokenAction = new TokenAction();
        tokenAction.setLogin(login);
        tokenAction.setPassword(password);
        send(tokenAction);
    }

    @Override
    public void authenticate(String token) {
        AuthenticateAction authAction = new AuthenticateAction();
        authAction.setToken(token);
        send(authAction);
    }

}
