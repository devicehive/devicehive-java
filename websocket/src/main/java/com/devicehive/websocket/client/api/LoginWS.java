package com.devicehive.websocket.client.api;

import com.devicehive.websocket.client.TokenResponseListener;
import com.devicehive.websocket.client.adapter.TokenDeserializer;
import com.devicehive.websocket.client.model.TokenAction;
import com.devicehive.websocket.client.model.TokenResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class LoginWS extends WebSocketListener {
    private WebSocket ws;
    private OkHttpClient client;
    private Gson gson = new Gson();
    private TokenResponseListener listener;

    public LoginWS(String url) {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        ws = client.newWebSocket(request, this);
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        if (listener != null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(TokenResponse.class, new TokenDeserializer())
                    .create();
            TokenResponse response = gson.fromJson(text, TokenResponse.class);
            if (response.isError()) {
                listener.onError(response.getError());
            } else {
                listener.onResult(response.getTokenVO());
            }
        }
    }


    public void sendMessage(String login, String password) {
        if (ws == null) {
            return;
        }
        TokenAction tokenAction = new TokenAction();
        tokenAction.setLogin(login);
        tokenAction.setPassword(password);
        String json = gson.toJson(tokenAction);
        ws.send(json);
    }


    public void subscribe(TokenResponseListener listener) {
        this.listener = listener;
    }

    public void unsubscribe() {
        client.dispatcher().executorService().shutdown();
        ws = null;
        listener = null;
    }

}
