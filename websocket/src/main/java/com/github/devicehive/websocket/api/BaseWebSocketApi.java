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

import com.github.devicehive.websocket.model.GsonHelper;
import com.google.gson.Gson;
import okhttp3.WebSocket;

public abstract class BaseWebSocketApi {
    private final com.github.devicehive.websocket.listener.ErrorListener listener;
    private WebSocket ws;
    Gson gson = GsonHelper.getInstance().getGsonFactory();

    public BaseWebSocketApi(WebSocket ws, com.github.devicehive.websocket.listener.ErrorListener listener) {
        this.ws = ws;
        this.listener = listener;
    }


    public abstract String getKey();

    com.github.devicehive.websocket.model.repsonse.ResponseAction getResponseAction(String text) {
        return gson.fromJson(text, com.github.devicehive.websocket.model.repsonse.ResponseAction.class);
    }

    void send(com.github.devicehive.websocket.model.request.RequestAction action) {
        ws.send(gson.toJson(action));
    }

    public abstract void onSuccess(String message);

    public void onMessage(String message) {
        com.github.devicehive.websocket.model.repsonse.ResponseAction action = getResponseAction(message);
        if (action.getStatus() == null || action.getStatus().equalsIgnoreCase(com.github.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS)) {
            onSuccess(message);
        } else if (action.getStatus().equalsIgnoreCase(com.github.devicehive.websocket.model.repsonse.ErrorResponse.ERROR)) {
            com.github.devicehive.websocket.model.repsonse.ErrorResponse errorResponse = gson.fromJson(message, com.github.devicehive.websocket.model.repsonse.ErrorResponse.class);
            listener.onError(errorResponse);
        }


    }

}
