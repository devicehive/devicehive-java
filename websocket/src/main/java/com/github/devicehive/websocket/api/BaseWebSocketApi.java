/*
 *
 *
 *   BaseWebSocketApi.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.websocket.api;

import com.github.devicehive.websocket.listener.ErrorListener;
import com.github.devicehive.websocket.model.GsonHelper;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.request.RequestAction;
import com.google.gson.Gson;
import okhttp3.WebSocket;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseWebSocketApi {
    private final ErrorListener listener;
    private final WebSocketClient client;
    private WebSocket ws;
    Gson gson = GsonHelper.getInstance().getGsonFactory();
    private List<String> lastMessage = new ArrayList<>(1);

    BaseWebSocketApi(WebSocketClient client, ErrorListener listener) {
        this.listener = listener;
        this.client = client;
        this.ws = client.getWebSocket();
    }


    public abstract String getKey();

    ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, ResponseAction.class);
    }

    void send(RequestAction action) {
        lastMessage.clear();
        lastMessage.add(gson.toJson(action));
        ws.send(lastMessage.get(0));
    }

    void repeatAction() {
        if (lastMessage.size() == 1) {
            ws.send(lastMessage.get(0));
        }
    }

    public abstract void onSuccess(String message);

    void onMessage(String message) {
        ResponseAction action = getResponseAction(message);
        if (action.getStatus() == null || action.getStatus().equalsIgnoreCase(ResponseAction.SUCCESS)) {
            onSuccess(message);
        } else if (action.getStatus().equals(ErrorResponse.ERROR)) {
            ErrorResponse errorResponse = gson.fromJson(message, ErrorResponse.class);
            if (action.getStatus().equals(ErrorResponse.ERROR)) {
                if (errorResponse.getCode() == 401) {
                    client.refresh();
                } else {
                    listener.onError(errorResponse);
                }
            }
        }
    }

}
