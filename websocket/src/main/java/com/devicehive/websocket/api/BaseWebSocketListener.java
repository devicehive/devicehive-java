package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.ErrorListener;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.AuthenticateAction;
import com.devicehive.websocket.model.request.RequestAction;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.devicehive.websocket.model.repsonse.ErrorResponse.ERROR;

public abstract class BaseWebSocketListener extends WebSocketListener {
    private final ErrorListener listener;
    private WebSocket ws;
    private Gson gson = new Gson();

    public BaseWebSocketListener(OkHttpClient client, Request request, ErrorListener listener) {
        ws = client.newWebSocket(request, this);
        this.listener = listener;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ResponseAction action = getResponseAction(text);
        if (action.getStatus().equalsIgnoreCase(ERROR)) {
            ErrorResponse errorResponse = gson.fromJson(text, ErrorResponse.class);
            listener.onError(errorResponse);
        } else {
            onSuccess(text);
        }

    }

    public abstract String getKey();

    public ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }

    public boolean send(RequestAction action) {
        System.out.println(gson.toJson(action));
        return ws.send(gson.toJson(action));
    }

    public void authenticate(String token) {
        AuthenticateAction authAction = new AuthenticateAction();
        authAction.setToken(token);
        send(authAction);
    }

    public void close() {
        ws.close(1000, null);
    }

    public abstract void onSuccess(String message);
}
