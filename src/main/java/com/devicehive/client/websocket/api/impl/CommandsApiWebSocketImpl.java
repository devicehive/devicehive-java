package com.devicehive.client.websocket.api.impl;


import com.devicehive.client.json.GsonFactory;
import com.devicehive.client.model.HiveMessageHandler;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.api.CommandsApiWebSocket;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.websocket.context.WebSocketClient;
import com.devicehive.client.websocket.model.DeviceCommand;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class CommandsApiWebSocketImpl implements CommandsApiWebSocket {

    private static Logger LOGGER = LoggerFactory.getLogger(CommandsApiWebSocketImpl.class);

    private static final String WS_COMMAND_INSERT = "command/insert";
    private static final String WS_COMMAND_UPDATE = "command/update";

    private static final String DEVICE_COMMANDS_COLLECTION_PATH = "/device/%s/command";
    private static final String DEVICE_COMMAND_RESOURCE_PATH = "/device/%s/command/%s";

    private final WebSocketClient WebSocketClient;
//    private DeviceCommandApi commandApi;

    /**
     * Initializes the API with a {@link WebSocketClient} to use for requests.
     *
     * @param WebSocketClient a websocket agent instance
     */
    public CommandsApiWebSocketImpl(WebSocketClient WebSocketClient) {
        this.WebSocketClient = WebSocketClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceCommand> queryCommands(String deviceGuid, Date start, Date end, String commandName,
                                             String status, String sortField, String sortOrder, Integer take,
                                             Integer skip, Integer gridInterval) throws HiveException {
        LOGGER.debug("DeviceCommand: query requested for device id {}, start timestamp {}, end timestamp {}, " +
                        "commandName {}, status {}, sort field {}, sort order {}, take param {}, skip param {}, grid interval {}",
                deviceGuid, start, end, commandName, status, sortField, sortOrder, take, skip, gridInterval);

        String path = String.format(DEVICE_COMMANDS_COLLECTION_PATH, deviceGuid);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start", start);
        queryParams.put("end", end);
        queryParams.put("command", commandName);
        queryParams.put("status", status);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);
        queryParams.put("gridInterval", gridInterval);

        List<DeviceCommand> result = WebSocketClient.execute(path, HttpMethod.GET, null, queryParams,
                new TypeToken<List<DeviceCommand>>() {
                }.getType(), COMMAND_LISTED);

        LOGGER.debug("DeviceCommand: query request processed successfully for device id {}, start timestamp {}, " +
                        "end timestamp {},commandName {}, status {}, sort field {}, sort order {}, take param {}, " +
                        "skip param {}, grid interval {}", deviceGuid, start, end, commandName, status, sortField, sortOrder,
                take, skip, gridInterval);

        return result;
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public DeviceCommand getCommand(String guid, long id) throws HiveException {
        LOGGER.debug("DeviceCommand: get requested for device id {} and command id {}", guid, id);

        String path = String.format(DEVICE_COMMAND_RESOURCE_PATH, guid, id);

        DeviceCommand result = WebSocketClient.execute(path, HttpMethod.GET, null, DeviceCommand.class, COMMAND_TO_DEVICE);

        LOGGER.debug("DeviceCommand: get request processed successfully for device id {} and command id {}. Date {}, " +
                        "userId {}, command {}, parameters {}, lifetime {}, status {}, result {}", guid, id,
                result.getTimestamp(), result.getUserId(), result.getCommand(), result.getParameters(), result.getLifetime(),
                result.getStatus(), result.getResult());

        return result;
    }

    @Override
    public DeviceCommand insertCommand(String guid, DeviceCommand command,
                                       HiveMessageHandler<DeviceCommand> commandUpdatesHandler) throws HiveException {
        if (command == null) {
            throw new HiveClientException("Command cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceCommand: insert requested for device id {} and command: command {}, parameters {}, " +
                "lifetime {}", guid, command.getCommand(), command.getParameters(), command.getLifetime());

        Gson gson = GsonFactory.createGson(COMMAND_FROM_CLIENT);

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_COMMAND_INSERT);
        request.addProperty("deviceId", guid);
        request.add("command", gson.toJsonTree(command));

        DeviceCommand result = WebSocketClient.sendMessage(request,
                "command",
                DeviceCommand.class, COMMAND_TO_CLIENT);
        if (commandUpdatesHandler != null) {
            WebSocketClient.subscribeForCommandUpdates(result.getId(), guid, commandUpdatesHandler);
        }

        LOGGER.debug("DeviceCommand: insert request proceed successfully for device id {] and command: command {}, " +
                        "parameters {}, lifetime {}, flags {}. Result command id {}, timestamp {}, userId {}",guid,
                command.getCommand(), command.getParameters(), command.getLifetime(), result.getId(),
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

        LOGGER.debug("DeviceCommand: update requested for device id {} and command: id {},  status {}, result {}",
                deviceId, command.getId(), command.getStatus(), command.getResult());

        Gson gson = GsonFactory.createGson(COMMAND_UPDATE_FROM_DEVICE);

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_COMMAND_UPDATE);
        request.addProperty("deviceGuid", deviceId);
        request.addProperty("commandId", command.getId());
        request.add("command", gson.toJsonTree(command));

        WebSocketClient.sendMessage(request);

        LOGGER.debug("DeviceCommand: update request proceed successfully for device id {] and command: id {}," +
                "status {}, result {}", deviceId, command.getId(), command.getStatus(), command.getResult());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForCommands(SubscriptionFilter filter, HiveMessageHandler<DeviceCommand> commandMessageHandler) throws HiveException {
        return WebSocketClient.subscribeForCommands(filter, commandMessageHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromCommands(String subId) throws HiveException {
        LOGGER.debug("Device: command/unsubscribe requested for subscription {}", subId);

        WebSocketClient.unsubscribeFromCommands(subId);

        LOGGER.debug("Device: command/unsubscribe request processed successfully");
    }

}
