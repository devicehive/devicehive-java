package com.devicehive.websocket.client.api;

import com.devicehive.websocket.client.TokenResponseListener;
import com.devicehive.websocket.client.model.DeviceAction;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class DeviceWS extends WebSocketListener {
    private WebSocket ws;
    private OkHttpClient client;
    private Gson gson = new Gson();
    private TokenResponseListener listener;

    public DeviceWS(String url,OkHttpClient client) {
        this.client = client;
        Request request = new Request.Builder().url(url).build();
        ws = client.newWebSocket(request, this);
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("DeviceWS: "+text);
    }


    public void sendMessage() {
        if (ws == null) {
            return;
        }
        DeviceAction action=new DeviceAction();
        action.setAction("device/list");
//        action.setSkip(-1);
        String json = gson.toJson(action);
        ws.send(json);
    }


    public void subscribe() {
        this.listener = listener;
    }

    public void unsubscribe() {
        client.dispatcher().executorService().shutdown();
        ws = null;
        listener = null;
    }

}
