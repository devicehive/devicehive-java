package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.ErrorListener;
import com.devicehive.websocket.model.GsonHelper;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.RequestAction;
import com.google.gson.Gson;
import okhttp3.WebSocket;

import static com.devicehive.websocket.model.repsonse.ErrorResponse.ERROR;
import static com.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

public abstract class BaseWebSocketApi {
    private final ErrorListener listener;
    private WebSocket ws;
    Gson gson = GsonHelper.getInstance().getGsonFactory();

    public BaseWebSocketApi(WebSocket ws, ErrorListener listener) {
        this.ws = ws;
        this.listener = listener;
    }


    public abstract String getKey();

    ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }

    void send(RequestAction action) {
        ws.send(gson.toJson(action));
    }

    public abstract void onSuccess(String message);

    public void onMessage(String message) {
        ResponseAction action = getResponseAction(message);
        if (action.getStatus() == null || action.getStatus().equalsIgnoreCase(SUCCESS)) {
            onSuccess(message);
        } else if (action.getStatus().equalsIgnoreCase(ERROR)) {
            ErrorResponse errorResponse = gson.fromJson(message, ErrorResponse.class);
            listener.onError(errorResponse);
        }


    }

}
