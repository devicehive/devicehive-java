package com.devicehive.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@Data
@AllArgsConstructor
public class WebSocketModel {

    WebSocket webSocket;
    WebSocketListener listener;
}
