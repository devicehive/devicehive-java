package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.TokenListener;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import com.devicehive.websocket.model.request.TokenCreateAction;
import com.devicehive.websocket.model.request.TokenGetAction;
import com.devicehive.websocket.model.request.TokenRefreshAction;
import com.devicehive.websocket.model.request.data.JwtPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.WebSocket;

import javax.annotation.Nullable;

import static com.devicehive.websocket.model.ActionConstant.*;

public class TokenWS extends BaseWebSocketApi implements TokenApi {
    public static final String TAG = "token";
    private TokenListener tokenListener;

    public TokenWS(WebSocket ws, TokenListener listener) {
        super(ws, listener);
        this.tokenListener = listener;
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
            tokenListener.onGet(tokenVO);
        } else if (actionName.equalsIgnoreCase(TOKEN_REFRESH)) {
            TokenRefreshResponse tokenVO = gson.fromJson(message, TokenRefreshResponse.class);
            tokenListener.onRefresh(tokenVO);

        } else if (actionName.equalsIgnoreCase(TOKEN_CREATE)) {
            TokenGetResponse tokenVO = gson.fromJson(message, TokenGetResponse.class);
            tokenListener.onCreate(tokenVO);

        }
    }

    @Override
    public void get(@Nullable Long requestId, String login, String password) {
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
    public void refresh(@Nullable Long requestId, String refreshToken) {
        TokenRefreshAction action = new TokenRefreshAction();
        action.setRefreshToken(refreshToken);
        action.setRequestId(requestId);
        send(action);
    }

}
