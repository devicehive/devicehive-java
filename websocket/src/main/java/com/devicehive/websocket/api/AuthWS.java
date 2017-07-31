package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.LoginListener;
import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.TokenAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

import static com.devicehive.websocket.model.ActionConstant.TOKEN;

public class AuthWS extends BaseWebSocketListener implements AuthApi {
    public static final String TAG = "AuthWS";
    private LoginListener loginListener;

    public AuthWS(OkHttpClient client, Request request, LoginListener listener) {
        super(client, request, listener);
        this.loginListener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory()).create();

        ResponseAction action = getResponseAction(message);
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(TOKEN)) {
            JwtTokenResponse tokenVO = gson.fromJson(message, JwtTokenResponse.class);
            authenticate(tokenVO.getAccessToken());
            loginListener.onResponse(tokenVO);
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println(t.getMessage());
    }

    @Override
    public void getToken(String login, String password, Long requestId) {
        TokenAction action = new TokenAction();
        action.setLogin(login);
        action.setPassword(password);
        action.setRequestId(requestId);
        send(action);
    }

}
