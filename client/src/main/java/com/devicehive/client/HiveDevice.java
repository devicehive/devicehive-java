package com.devicehive.client;

import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.Device;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.exceptions.HiveException;

import java.sql.Timestamp;
import java.util.List;

/**
 * HiveDevice represents a simple device in terms of DeviceHive. After connection is established, devices needs to
 * register, authenticate and then start sending notifications. Devices may also subscribe to commands and
 * then start receiving server-originated messages about new commands.
 */
public interface HiveDevice extends AutoCloseable {

    /**
     * Authenticates a device.
     *
     * @param deviceId  device identifier
     * @param deviceKey device key
     * @throws HiveException
     */
    void authenticate(String deviceId, String deviceKey) throws HiveException;

    /**
     * Gets information about the current device.
     *
     * @return current device info
     * @throws HiveException
     */
    Device getDevice() throws HiveException;

    /**
     * Registers or updates a device.
     *
     * @param device update/create device info
     * @throws HiveException
     */
    void registerDevice(Device device) throws HiveException;

    /**
     * Queries device commands.
     *
     * @param start   command start timestamp (UTC).
     * @param end     command end timestamp (UTC).
     * @param command command name.
     * @param status  command status.
     * @param sortBy  Result list sort field. Available values are Timestamp (default), Command and Status.
     * @param sortAsc if true - ascending sort order in the result list will be used, descending otherwise
     * @param take    Number of records to take from the result list (default is 1000).
     * @param skip    Number of records to skip from the result list.
     * @return list of device commands
     * @throws HiveException
     */
    List<DeviceCommand> queryCommands(Timestamp start, Timestamp end, String command, String status,
                                      String sortBy, boolean sortAsc, Integer take, Integer skip) throws HiveException;

    /**
     * Gets information about device command.
     *
     * @param commandId command identifier
     * @return existing device command
     * @throws HiveException
     */
    DeviceCommand getCommand(long commandId) throws HiveException;

    /**
     * Updates an existing device command.
     *
     * @param deviceCommand update info in the device command representation
     * @throws HiveException
     */
    void updateCommand(DeviceCommand deviceCommand) throws HiveException;

    /**
     * Subscribes the device to commands. After subscription is completed, the server will start to send commands to the
     * connected device.
     *
     * @param timestamp Timestamp of the last received command (UTC). If not specified, the server's timestamp is taken
     *                  instead.
     * @param commandsHandler a delegate that handles received commands
     * @throws HiveException
     */
    void subscribeForCommands(Timestamp timestamp, HiveMessageHandler<DeviceCommand> commandsHandler)
        throws HiveException;

    /**
     * Unsubscribes the device from commands.
     *
     * @throws HiveException
     */
    void unsubscribeFromCommands() throws HiveException;

    /**
     * Creates new device notification on behalf of device.
     *
     * @param deviceNotification device notification that should be created
     * @return info about inserted notification
     * @throws HiveException
     */
    DeviceNotification insertNotification(DeviceNotification deviceNotification) throws HiveException;

    /**
     * Requests API info from server.
     *
     * @return API info
     * @throws HiveException
     */
    ApiInfo getInfo() throws HiveException;

    /**
     * Disconnects the device from the server.
     */
    void close();

}
