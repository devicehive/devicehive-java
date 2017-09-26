package com.devicehive.client.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * |-- save()  save current state to server
 * |-- getCommands(start_timestamp, end_timestamp, max_ number)
 * |     returns list of DeviceCommand objects
 * |-- getNotifications(start_timestamp, end_timestamp, max_ number)
 * |     returns list of DeviceNotification objects
 * |-- sendCommand(command, parameters, result_callback) returns DeviceCommand object
 * |-- sendNotification(name, parameters)
 * |-- subscribeCommands(callback, commandFilter), callback_proto(deviceCommand)
 * |-- subscribeNotifications(callback, nameFilter), callback_proto(deviceNotification)
 * |-- unsubscribeCommands(commandFilter)
 * -- unsubscribeNotifications(nameFilter)
 */
interface DeviceInterface {

    void save();

    List<DeviceCommand> getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber);

    List<DeviceNotification> getNotifications(DateTime startTimestamp, DateTime endTimestamp, int maxNumber);

    DeviceCommand sendCommand(DeviceCommand command, String parameters, DeviceCommandCallback resultCallback);

    DeviceNotification sendNotification(String notification, String parameters);

    //TODO Find callback that is needed
    void subscribeCommands(DeviceCommandCallback callback, CommandFilter commandFilter);

    void subscribeNotifications(DeviceNotificationCallback callback, NameFilter nameFilter);

    void unsubscribeCommands(CommandFilter commandFilter);

    void unsubscribeNotifications(NameFilter nameFilter);
}
