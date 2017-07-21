package com.devicehive.client.websocket;

import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.api.impl.HiveClientWebSocketImplementation;
import com.devicehive.client.websocket.context.RestClientWIP;
import com.devicehive.client.websocket.context.WebSocketClient;

import java.net.URI;

public final class HiveFactory {

    private HiveFactory() {
    }


    private static RestClientWIP createRestAgent(URI restUri) throws HiveException {
        RestClientWIP agent = new RestClientWIP(restUri);
        agent.connect();
        return agent;
    }

    public static HiveClientWebSocketImplementation createWSclient(URI restUri) throws HiveException {
        return new HiveClientWebSocketImplementation(createWebsocketClientAgent(restUri));
    }

    private static WebSocketClient createWebsocketClientAgent(URI restUri) throws HiveException {
        WebSocketClient agent = new WebSocketClient(restUri);
        agent.connect();
        return agent;
    }

}