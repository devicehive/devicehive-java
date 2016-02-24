package com.devicehive.client.websocket;

import com.devicehive.client.api.HiveClient;
import com.devicehive.client.websocket.api.impl.HiveClientRestImpl;
import com.devicehive.client.websocket.api.impl.HiveClientWebSocketImpl;
import com.devicehive.client.websocket.context.RestClientWIP;
import com.devicehive.client.websocket.context.WebSocketClient;
import com.devicehive.client.model.exceptions.HiveException;
import com.sun.webpane.sg.WebPageClientImpl;

import java.net.URI;

public final class HiveFactory {

    private HiveFactory() {
    }


    /**
     * Creates an instance of {@link HiveClient} connected to the Device Hive server.
     *
     * @param restUri          the Device Hive server RESTful API URI
     * @param preferWebsockets if set to {@code true}, the client will attempt to use websockets for communication with
     *                         the server.
     * @return an instance of {@link HiveClient}
     * @throws HiveException if a connection error occurs
     */
//    public static HiveClient createClient(URI restUri, boolean preferWebsockets) throws HiveException {
//        if (preferWebsockets) {
//            return new HiveClientWebSocketImpl(createWebsocketClientAgent(restUri));
//        } else {
//            return new HiveClientRestImpl(createRestAgent(restUri));
//        }
//    }
    private static RestClientWIP createRestAgent(URI restUri) throws HiveException {
        RestClientWIP agent = new RestClientWIP(restUri);
        agent.connect();
        return agent;
    }

    public static HiveClientWebSocketImpl createWSclient(URI restUri) throws HiveException {
        return new HiveClientWebSocketImpl(createWebsocketClientAgent(restUri));
    }

    private static WebSocketClient createWebsocketClientAgent(URI restUri) throws HiveException {
        WebSocketClient agent = new WebSocketClient(restUri);
        agent.connect();
        return agent;
    }

}