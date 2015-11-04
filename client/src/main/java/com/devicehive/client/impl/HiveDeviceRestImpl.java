package com.devicehive.client.impl;


import com.devicehive.client.HiveDevice;
import com.devicehive.client.HiveMessageHandler;
import com.devicehive.client.impl.context.HivePrincipal;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.impl.util.HiveValidator;
import com.devicehive.client.model.*;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.tuple.Pair;

import javax.ws.rs.HttpMethod;
import java.sql.Timestamp;
import java.util.*;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Implementation of {@link HiveDevice} that uses REST transport.
 */
public class HiveDeviceRestImpl implements HiveDevice {

    protected static final String DEVICE_RESOURCE_PATH = "/device/%s";
    protected static final String DEVICE_COMMANDS_COLLECTION_PATH = "/device/%s/command";
    protected static final String DEVICE_COMMAND_RESOURCE_PATH = "/device/%s/command/%s";
    protected static final String DEVICE_NOTIFICATIONS_COLLECTION_PATH = "/device/%s/notification";

    protected RestAgent restAgent;
    protected String commandsSubscriptionId;

    /**
     * Initializes the device with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    public HiveDeviceRestImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * Closes single hive device with associated context
     */
    @Override
    public void close() {
        restAgent.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String deviceId, String deviceKey) throws HiveException {
        restAgent.authenticate(HivePrincipal.createDevice(deviceId, deviceKey));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device getDevice() throws HiveException {
        String deviceId = restAgent.getHivePrincipal().getPrincipal().getKey();
        if (deviceId == null) {
            throw new HiveClientException("Device is not authenticated");
        }

        String path = String.format(DEVICE_RESOURCE_PATH, deviceId);
        return restAgent.execute(path, HttpMethod.GET, null, Device.class, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDevice(Device device) throws HiveException {
        HiveValidator.validate(device);

        String path = String.format(DEVICE_COMMANDS_COLLECTION_PATH, device.getId());
        restAgent.execute(path, HttpMethod.PUT, null, device, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceCommand> queryCommands(Timestamp start, Timestamp end, String command, String status,
                                             String sortBy, boolean sortAsc, Integer take, Integer skip) throws HiveException {
        Pair<String, String> authenticated = restAgent.getHivePrincipal().getPrincipal();
        String path = String.format(DEVICE_COMMANDS_COLLECTION_PATH, authenticated.getKey());

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start", start);
        queryParams.put("end", end);
        queryParams.put("command", command);
        queryParams.put("status", status);
        queryParams.put("sortField", sortBy);
        String order = sortAsc ? "ASC" : "DESC";
        queryParams.put("sortOrder", order);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        return restAgent.execute(path, HttpMethod.GET, null, queryParams,
            new TypeToken<List<DeviceCommand>>() {}.getType(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommand getCommand(long commandId) throws HiveException {
        Pair<String, String> authenticated = restAgent.getHivePrincipal().getPrincipal();
        String path = String.format(DEVICE_COMMAND_RESOURCE_PATH, authenticated.getKey(), commandId);

        return restAgent.execute(path, HttpMethod.GET, null, DeviceCommand.class, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCommand(DeviceCommand deviceCommand) throws HiveException {
        Pair<String, String> authenticated = restAgent.getHivePrincipal().getPrincipal();
        String path = String.format(DEVICE_COMMAND_RESOURCE_PATH, authenticated.getKey(), deviceCommand.getId());

        restAgent.execute(path, HttpMethod.PUT, null, deviceCommand, COMMAND_UPDATE_FROM_DEVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribeForCommands(final Timestamp timestamp,
                                     final HiveMessageHandler<DeviceCommand> commandsHandler) throws HiveException {
        Set<String> uuids = Collections.singleton(restAgent.getHivePrincipal().getPrincipal().getLeft());
        SubscriptionFilter filter = new SubscriptionFilter(uuids, null, timestamp);

        commandsSubscriptionId = restAgent.subscribeForCommands(filter, commandsHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceNotification insertNotification(DeviceNotification deviceNotification) throws HiveException {
        Pair<String, String> authenticated = restAgent.getHivePrincipal().getPrincipal();
        HiveValidator.validate(deviceNotification);

        String path = String.format(DEVICE_NOTIFICATIONS_COLLECTION_PATH, authenticated.getKey());
        return restAgent.execute(path, HttpMethod.POST, null, null, deviceNotification, DeviceNotification.class,
            NOTIFICATION_FROM_DEVICE, NOTIFICATION_TO_DEVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiInfo getInfo() throws HiveException {
        return restAgent.getInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromCommands() throws HiveException {
        restAgent.unsubscribeFromCommands(commandsSubscriptionId);
    }

}
