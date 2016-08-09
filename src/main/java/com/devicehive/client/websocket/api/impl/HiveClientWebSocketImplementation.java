package com.devicehive.client.websocket.api.impl;


import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.context.HivePrincipal;
import com.devicehive.client.websocket.context.WebSocketClient;
import org.joda.time.DateTime;

/**
 * Specialization of {@link HiveClientRestImpl} that uses WebSocket transport for requests.
 */
public class HiveClientWebSocketImplementation {

    private final WebSocketClient WebSocketClient;

    /**
     * Initializes the client with {@link WebSocketClient} to use for requests.
     *
     * @param WebSocketClient a WebSocketClient to use for requests
     */
    public HiveClientWebSocketImplementation(WebSocketClient WebSocketClient) {
        this.WebSocketClient = WebSocketClient;
    }

    /**
     * {@inheritDoc}
     */
    public void authenticate(String login, String password) throws HiveException {
        WebSocketClient.authenticate(HivePrincipal.createUser(login, password));
    }

    /**
     * {@inheritDoc}
     */
    public void authenticate(String accessKey) throws HiveException {
        WebSocketClient.authenticate(HivePrincipal.createAccessKey(accessKey));
    }

    /**
     * {@inheritDoc}
     */

    public CommandsApiWebSocketImpl getCommandsWSAPI() {
        return new CommandsApiWebSocketImpl(WebSocketClient);
    }


    /**
     * {@inheritDoc}
     */
    public NotificationsAPIWebsocketImpl getNotificationsWSAPI() {
        return new NotificationsAPIWebsocketImpl(WebSocketClient);
    }

    public DateTime getTimestamp() throws HiveException{
        return WebSocketClient.getInfo().getServerTimestamp();
    }
}
