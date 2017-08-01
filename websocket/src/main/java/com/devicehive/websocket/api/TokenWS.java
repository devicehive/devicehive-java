package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.LoginListener;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import com.devicehive.websocket.model.request.TokenCreateAction;
import com.devicehive.websocket.model.request.TokenGetAction;
import com.devicehive.websocket.model.request.TokenRefreshAction;
import com.devicehive.websocket.model.request.data.JwtPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.TOKEN_CREATE;
import static com.devicehive.websocket.model.ActionConstant.TOKEN_GET;
import static com.devicehive.websocket.model.ActionConstant.TOKEN_REFRESH;

public class TokenWS extends BaseWebSocketListener implements TokenApi {
    public static final String TAG = "TokenWS";
    private LoginListener loginListener;

    public TokenWS(OkHttpClient client, Request request, LoginListener listener) {
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
        if (actionName.equalsIgnoreCase(TOKEN_GET)) {
            TokenGetResponse tokenVO = gson.fromJson(message, TokenGetResponse.class);
            loginListener.onGet(tokenVO);
        } else if (actionName.equalsIgnoreCase(TOKEN_REFRESH)) {
            TokenRefreshResponse tokenVO = gson.fromJson(message, TokenRefreshResponse.class);
            loginListener.onRefresh(tokenVO);

        } else if (actionName.equalsIgnoreCase(TOKEN_CREATE)) {
            TokenGetResponse tokenVO = gson.fromJson(message, TokenGetResponse.class);
            loginListener.onCreate(tokenVO);

        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println(t.getMessage());
    }

    @Override
    public void get(String login, String password, Long requestId) {
        TokenGetAction action = new TokenGetAction();
        action.setLogin(login);
        action.setPassword(password);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void create(@Nullable Long requestId, JwtPayload payload) {
        TokenCreateAction action = new TokenCreateAction();
        action.setRequestId(requestId);
        action.setPayload(payload);
        send(action);
    }

    @Override
    public void refresh(String refreshToken, @Nullable Long requestId) {
        TokenRefreshAction action = new TokenRefreshAction();
        action.setRefreshToken(refreshToken);
        action.setRequestId(requestId);
        send(action);
    }

}
