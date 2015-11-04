package com.devicehive.client;


import com.devicehive.client.impl.HiveClientRestImpl;
import com.devicehive.client.impl.HiveClientWebsocketImpl;
import com.devicehive.client.impl.HiveDeviceRestImpl;
import com.devicehive.client.impl.HiveDeviceWebsocketImpl;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.impl.context.WebsocketAgent;
import com.devicehive.client.model.exceptions.HiveException;

import java.net.URI;

/**
 * A factory class that provides static methods to create {@link HiveClient} and {@link HiveDevice} instances.
 */
public final class HiveFactory {

    private HiveFactory() {
    }

    /**
     * Creates an instance of {@link HiveClient} connected to the Device Hive server. Allows the user to specify
     * callbacks to invoke when a websocket connection state changes.
     *
     * @param restUri the Device Hive server RESTful API URI
     * @param preferWebsockets if set to {@code true}, the client will attempt to use websockets for communication with
     *                         the server, falling back to the long polling if the attempt fails.
     * @param connectionLostCallback (optional) a callback that gets invoked when a websocket connection is lost
     * @param connectionRestoredCallback (optional) a callback that gets invoked when a websocket connection is restored
     * @return an instance of {@link HiveClient}
     * @throws HiveException if a connection error occurs
     */
    public static HiveClient createClient(URI restUri,
                                          boolean preferWebsockets,
                                          ConnectionLostCallback connectionLostCallback,
                                          ConnectionRestoredCallback connectionRestoredCallback) throws HiveException {
        if (preferWebsockets) {
            return new HiveClientWebsocketImpl(
                createWebsocketClientAgent(restUri, connectionLostCallback, connectionRestoredCallback));
        } else {
            return new HiveClientRestImpl(createRestAgent(restUri));
        }
    }

    /**
     * Creates an instance of {@link HiveClient} connected to the Device Hive server.
     *
     * @param restUri the Device Hive server RESTful API URI
     * @param preferWebsockets if set to {@code true}, the client will attempt to use websockets for communication with
     *                         the server, falling back to the long polling if the attempt fails.
     * @return an instance of {@link HiveClient}
     * @throws HiveException if a connection error occurs
     */
    public static HiveClient createClient(URI restUri, boolean preferWebsockets) throws HiveException {
        return createClient(restUri, preferWebsockets, null, null);
    }

    /**
     * Creates an instance of {@link HiveDevice} connected to the Device Hive server. Allows a user to specify callbacks
     * to invoke when a websocket connection state changes.
     *
     * @param restUri the Device Hive server RESTful API URI
     * @param preferWebsockets if set to {@code true}, the device will attempt to use websockets for communication with
     *                         the server, falling back to the long polling if the attempt fails.
     * @param connectionLostCallback (optional) a callback that gets invoked when a websocket connection is lost
     * @param connectionRestoredCallback (optional) a callback that gets invoked when a websocket connection is restored
     * @return an instance of {@link HiveDevice}
     * @throws HiveException if a connection error occurs
     */
    public static HiveDevice createDevice(URI restUri,
                                          boolean preferWebsockets,
                                          ConnectionLostCallback connectionLostCallback,
                                          ConnectionRestoredCallback connectionRestoredCallback) throws HiveException {
        if (preferWebsockets) {
            return new HiveDeviceWebsocketImpl(
                createWebsocketDeviceAgent(restUri, connectionLostCallback, connectionRestoredCallback));
        } else {
            return new HiveDeviceRestImpl(createRestAgent(restUri));
        }
    }

    /**
     * Creates an instance of {@link HiveDevice} connected to the Device Hive server.
     *
     * @param restUri the Device Hive server RESTful API URI
     * @param preferWebsockets if set to {@code true}, the device will attempt to use websockets for communication with
     *                         the server, falling back to the long polling if the attempt fails.
     * @return an instance of {@link HiveDevice}
     * @throws HiveException if a connection error occurs
     */
    public static HiveDevice createDevice(URI restUri, boolean preferWebsockets) throws HiveException {
        return createDevice(restUri, preferWebsockets, null, null);
    }

    private static RestAgent createRestAgent(URI restUri) throws HiveException {
        RestAgent agent = new RestAgent(restUri);
        agent.connect();
        return agent;
    }

    private static WebsocketAgent createWebsocketClientAgent(URI restUri,
                                                             ConnectionLostCallback connectionLostCallback,
                                                             ConnectionRestoredCallback connectionRestoredCallback)
                                                             throws HiveException {
        WebsocketAgent agent = new WebsocketAgent(restUri, WebsocketAgent.Role.CLIENT, connectionLostCallback,
            connectionRestoredCallback);
        agent.connect();
        return agent;
    }

    private static WebsocketAgent createWebsocketDeviceAgent(URI restUri,
                                                             ConnectionLostCallback connectionLostCallback,
                                                             ConnectionRestoredCallback connectionRestoredCallback)
                                                             throws HiveException {
        WebsocketAgent agent = new WebsocketAgent(restUri, WebsocketAgent.Role.DEVICE, connectionLostCallback,
            connectionRestoredCallback);
        agent.connect();
        return agent;
    }
}
