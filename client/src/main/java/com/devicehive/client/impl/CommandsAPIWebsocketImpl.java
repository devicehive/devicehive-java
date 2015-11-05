package com.devicehive.client.impl;


import com.devicehive.client.HiveMessageHandler;
import com.devicehive.client.impl.context.WebsocketAgent;
import com.devicehive.client.impl.json.GsonFactory;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.SubscriptionFilter;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Specification of {@link CommandsControllerRestImpl} that uses WebSockets as a transport.
 */
class CommandsAPIWebsocketImpl extends CommandsAPIRestImpl {
    private static Logger LOGGER = LoggerFactory.getLogger(CommandsAPIWebsocketImpl.class);

    private static final String WS_COMMAND_INSERT = "command/insert";
    private static final String WS_COMMAND_UPDATE = "command/update";

    private final WebsocketAgent websocketAgent;

    /**
     * Initializes the controller with a {@link WebsocketAgent} to use for requests.
     *
     * @param websocketAgent a websocket agent instance
     */
    CommandsAPIWebsocketImpl(WebsocketAgent websocketAgent) {
        super(websocketAgent);
        this.websocketAgent = websocketAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommand insertCommand(String guid, DeviceCommand command,
                                       HiveMessageHandler<DeviceCommand> commandUpdatesHandler) throws HiveException {
        if (command == null) {
            throw new HiveClientException("Command cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceCommand: insert requested for device id {} and command: command {}, parameters {}, " +
            "lifetime {}, flags {}", guid, command.getCommand(), command.getParameters(), command.getLifetime(),
             command.getFlags());

        Gson gson = GsonFactory.createGson(COMMAND_FROM_CLIENT);

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_COMMAND_INSERT);
        request.addProperty("deviceGuid", guid);
        request.add("command", gson.toJsonTree(command));

        DeviceCommand result = websocketAgent.sendMessage(request, "command", DeviceCommand.class, COMMAND_TO_CLIENT);

        if (commandUpdatesHandler != null) {
            websocketAgent.subscribeForCommandUpdates(result.getId(), guid, commandUpdatesHandler);
        }

        LOGGER.debug("DeviceCommand: insert request proceed successfully for device id {] and command: command {}, " +
            "parameters {}, lifetime {}, flags {}. Result command id {}, timestamp {}, userId {}", guid,
            command.getCommand(), command.getParameters(), command.getLifetime(), command.getFlags(), result.getId(),
            result.getTimestamp(), result.getUserId());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCommand(String deviceId, DeviceCommand command) throws HiveException {
        if (command == null) {
            throw new HiveClientException("Command cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (command.getId() == null) {
            throw new HiveClientException("Command id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceCommand: update requested for device id {} and command: id {},  flags {}, status {}, result {}",
            deviceId, command.getId(), command.getFlags(), command.getStatus(), command.getResult());

        Gson gson = GsonFactory.createGson(COMMAND_UPDATE_FROM_DEVICE);

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_COMMAND_UPDATE);
        request.addProperty("deviceGuid", deviceId);
        request.addProperty("commandId", command.getId());
        request.add("command", gson.toJsonTree(command));

        websocketAgent.sendMessage(request);

        LOGGER.debug("DeviceCommand: update request proceed successfully for device id {] and command: id {}, flags {}, " +
            "status {}, result {}", deviceId, command.getId(), command.getFlags(), command.getStatus(), command.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForCommands(SubscriptionFilter filter,
                                       HiveMessageHandler<DeviceCommand> commandMessageHandler) throws HiveException {
        LOGGER.debug("Client: notification/subscribe requested for filter {},", filter);

        return websocketAgent.subscribeForCommands(filter, commandMessageHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromCommands(String subId) throws HiveException {
        LOGGER.debug("Device: command/unsubscribe requested for subscription {}", subId);

        websocketAgent.unsubscribeFromCommands(subId);

        LOGGER.debug("Device: command/unsubscribe request processed successfully");
    }

}
