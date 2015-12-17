package com.devicehive.client.websocket.api.impl;


import com.devicehive.client.api.CommandsSubscriptor;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.json.GsonFactory;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandWrapper;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.websocket.context.WebsocketAgent;
import com.devicehive.client.model.HiveMessageHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.Date;
import java.util.List;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Specialization of {@link com.devicehive.client.websocket.api.impl.CommandsAPIRestImpl} that uses WebSockets as a transport.
 */
class CommandsAPIWebsocketImpl extends CommandsAPIRestImpl implements DeviceCommandApi, CommandsSubscriptor {

    private static Logger LOGGER = LoggerFactory.getLogger(CommandsAPIWebsocketImpl.class);

    private static final String WS_COMMAND_INSERT = "command/insert";
    private static final String WS_COMMAND_UPDATE = "command/update";

    private final WebsocketAgent websocketAgent;
    private DeviceCommandApi commandApi;

    /**
     * Initializes the API with a {@link WebsocketAgent} to use for requests.
     *
     * @param websocketAgent a websocket agent instance
     */
    CommandsAPIWebsocketImpl(WebsocketAgent websocketAgent) {
        super(websocketAgent);
        this.websocketAgent = websocketAgent;
        commandApi = this.websocketAgent.createService(DeviceCommandApi.class);
    }

    @Override
    public List<DeviceCommand> queryCommands(String deviceGuid, Date start, Date end, String commandName, String status, String sortField, String sortOrder, Integer take, Integer skip, Integer gridInterval) throws HiveException {
        return null;
    }

    @Override
    public DeviceCommand getCommand(String guid, long id) throws HiveException {
        return null;
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
                "lifetime {}", guid, command.getCommand(), command.getParameters(), command.getLifetime());

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

        websocketAgent.sendMessage(request);

        LOGGER.debug("DeviceCommand: update request proceed successfully for device id {] and command: id {}," +
                "status {}, result {}", deviceId, command.getId(), command.getStatus(), command.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForCommands(SubscriptionFilter filter, HiveMessageHandler<com.devicehive.client.model.DeviceCommand> commandMessageHandler) throws HiveException {
        return null;
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


    @Override
    public List<com.devicehive.client.model.DeviceCommand> pollMany(@Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout) {
        return null;
    }

    @Override
    public void pollMany(@Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, Callback<List<com.devicehive.client.model.DeviceCommand>> cb) {

    }

    @Override
    public List<com.devicehive.client.model.DeviceCommand> query(@Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval) {
        return null;
    }

    @Override
    public void query(@Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval, Callback<List<com.devicehive.client.model.DeviceCommand>> cb) {

    }

    @Override
    public com.devicehive.client.model.DeviceCommand insert(@Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body) {
        return null;
    }

    @Override
    public void insert(@Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body, Callback<com.devicehive.client.model.DeviceCommand> cb) {

    }

    @Override
    public List<com.devicehive.client.model.DeviceCommand> poll(@Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout) {
        return null;
    }

    @Override
    public void poll(@Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, Callback<List<com.devicehive.client.model.DeviceCommand>> cb) {

    }

    @Override
    public com.devicehive.client.model.DeviceCommand get(@Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId) {
        return null;
    }

    @Override
    public void get(@Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, Callback<com.devicehive.client.model.DeviceCommand> cb) {

    }

    @Override
    public com.devicehive.client.model.DeviceCommand update(@Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body) {
        return null;
    }

    @Override
    public void update(@Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body, Callback<com.devicehive.client.model.DeviceCommand> cb) {

    }

    @Override
    public List<com.devicehive.client.model.DeviceCommand> wait(@Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout) {
        return null;
    }

    @Override
    public void wait(@Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout, Callback<List<com.devicehive.client.model.DeviceCommand>> cb) {

    }
}
