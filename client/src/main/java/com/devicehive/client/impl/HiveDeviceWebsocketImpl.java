package com.devicehive.client.impl;


import com.devicehive.client.HiveMessageHandler;
import com.devicehive.client.impl.context.HivePrincipal;
import com.devicehive.client.impl.context.WebsocketAgent;
import com.devicehive.client.impl.json.GsonFactory;
import com.devicehive.client.model.Device;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.SubscriptionFilter;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import javax.ws.rs.HttpMethod;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Specialization of {@link HiveDeviceRestImpl} that uses WebSockets as a transport.
 */
public class HiveDeviceWebsocketImpl extends HiveDeviceRestImpl {

    private static final String WS_DEVICE_GET = "device/get";
    private static final String WS_DEVICE_SAVE = "device/save";
    private static final String WS_COMMAND_UPDATE = "command/update";
    private static final String WS_NOTIFICATION_INSERT = "notification/insert";

    private static final String RESPONSE_MEMBER_DEVICE = "device";
    private static final String RESPONSE_MEMBER_NOTIFICATION = "notification";

    private WebsocketAgent websocketAgent;

    /**
     * Initializes a device with {@link WebsocketAgent} to use for requests.
     *
     * @param websocketAgent a WebsocketAgent to use for requests
     */
    public HiveDeviceWebsocketImpl(WebsocketAgent websocketAgent) {
        super(websocketAgent);
        this.websocketAgent = websocketAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String deviceId, String deviceKey) throws HiveException {
        super.authenticate(deviceId, deviceKey);
        websocketAgent.authenticate(HivePrincipal.createDevice(deviceId, deviceKey));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device getDevice() throws HiveException {
        JsonObject request = new JsonObject();
        request.addProperty("action", WS_DEVICE_GET);
        return websocketAgent.sendMessage(request, RESPONSE_MEMBER_DEVICE, Device.class, DEVICE_PUBLISHED_DEVICE_AUTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDevice(Device device) throws HiveException {
        Gson gson = GsonFactory.createGson();

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_DEVICE_SAVE);
        request.add("device", gson.toJsonTree(device));

        if (websocketAgent.getHivePrincipal() != null) {
            Pair<String, String> authenticated = websocketAgent.getHivePrincipal().getPrincipal();
            request.addProperty("deviceId", authenticated.getLeft());
            request.addProperty("deviceKey", authenticated.getRight());
        } else {
            request.addProperty("deviceId", device.getId());
            request.addProperty("deviceKey", device.getKey());
        }

        websocketAgent.sendMessage(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommand getCommand(long commandId) throws HiveException {
        Pair<String, String> authenticated = websocketAgent.getHivePrincipal().getPrincipal();
        String path = String.format(DEVICE_COMMAND_RESOURCE_PATH, authenticated.getKey(), commandId);

        return websocketAgent.execute(path, HttpMethod.GET, null, DeviceCommand.class, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCommand(DeviceCommand deviceCommand) throws HiveException {
        Gson gson = GsonFactory.createGson(COMMAND_UPDATE_FROM_DEVICE);
        String requestId = UUID.randomUUID().toString();

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_COMMAND_UPDATE);
        request.addProperty("requestId", requestId);
        request.addProperty("commandId", deviceCommand.getId());
        request.add("command", gson.toJsonTree(deviceCommand));

        websocketAgent.sendMessage(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribeForCommands(final Timestamp timestamp,
                                     final HiveMessageHandler<DeviceCommand> commandMessageHandler) throws HiveException {
        Set<String> uuids = Collections.singleton(websocketAgent.getHivePrincipal().getPrincipal().getLeft());
        SubscriptionFilter filter = new SubscriptionFilter(uuids, null, timestamp);

        commandsSubscriptionId = websocketAgent.subscribeForCommands(filter, commandMessageHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromCommands() throws HiveException {
        websocketAgent.unsubscribeFromCommands(commandsSubscriptionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceNotification insertNotification(DeviceNotification deviceNotification) throws HiveException {
        Gson gson = GsonFactory.createGson(NOTIFICATION_FROM_DEVICE);

        JsonObject request = new JsonObject();
        request.addProperty("action", WS_NOTIFICATION_INSERT);
        request.add("notification", gson.toJsonTree(deviceNotification));

        return websocketAgent.sendMessage(request, RESPONSE_MEMBER_NOTIFICATION, DeviceNotification.class,
            NOTIFICATION_TO_DEVICE);
    }
}
