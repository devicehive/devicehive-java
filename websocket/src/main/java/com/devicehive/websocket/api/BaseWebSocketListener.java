package com.devicehive.websocket.api;

import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public abstract class BaseWebSocketListener extends WebSocketListener {
    private WebSocket ws;
    private Gson gson = new Gson();

    public BaseWebSocketListener(OkHttpClient client, Request request) {
        ws = client.newWebSocket(request, this);
    }

    public WebSocket getWebSocketConnection() {
        return ws;
    }

    public Gson getGson() {
        return gson;
    }

    public ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }


}
