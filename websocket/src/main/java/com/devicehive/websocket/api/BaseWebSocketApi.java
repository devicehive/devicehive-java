package com.devicehive.websocket.api;

import com.devicehive.websocket.adapter.JsonStringWrapperAdapterFactory;
import com.devicehive.websocket.listener.ErrorListener;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.request.RequestAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.WebSocket;

import static com.devicehive.websocket.model.repsonse.ErrorResponse.ERROR;

public abstract class BaseWebSocketApi {
    private final ErrorListener listener;
    private WebSocket ws;
    private Gson gson = new Gson();

    public BaseWebSocketApi(WebSocket ws, ErrorListener listener) {
        this.ws = ws;
        this.listener = listener;
    }


    public abstract String getKey();

    ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }

    boolean send(RequestAction action) {
        return ws.send(gson.toJson(action));
    }

    public abstract void onSuccess(String message);

    public void onMessage(String message){
        ResponseAction action = getResponseAction(message);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .create();
        if (action.getStatus().equalsIgnoreCase(ERROR)) {
            ErrorResponse errorResponse = gson.fromJson(message, ErrorResponse.class);
            listener.onError(errorResponse);
        }else {
            onSuccess(message);
        }
    }

}
