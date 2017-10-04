package com.devicehive.client.service;

import com.devicehive.client.DeviceHive;
import com.devicehive.client.model.*;
import com.devicehive.rest.model.JsonStringWrapper;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Device implements DeviceInterface {


    private String id = null;

    private String name = null;

    private JsonStringWrapper data = null;

    private Long networkId = null;

    private Boolean isBlocked = false;
    private DeviceCommandsCallback commandCallback;
    private DeviceNotificationsCallback notificationCallback;

    private Device() {
    }

    static Device create(com.devicehive.rest.model.Device device) {
        if (device == null) {
            return null;
        }
        Device result = new Device();
        result.id = device.getId();
        result.name = device.getName();
        result.data = device.getData();
        result.networkId = device.getNetworkId();
        result.isBlocked = device.getIsBlocked();
        return result;
    }

    static List<Device> list(List<com.devicehive.rest.model.Device> devices) {
        List<Device> list = new ArrayList<Device>();
        if (devices == null) {
            return Collections.emptyList();
        }
        for (com.devicehive.rest.model.Device device :
                devices) {
            list.add(Device.create(device));

        }
        return list;
    }

    public void save() {
        DeviceHive.getInstance().putDevice(id, name);
    }

    public List<DeviceCommand> getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) {
        return DeviceHive.getInstance().getCommandService()
                .getDeviceCommands(this.id, startTimestamp, endTimestamp, maxNumber).getData();
    }

    public List<DeviceNotification> getNotifications(DateTime startTimestamp, DateTime endTimestamp) {
        return DeviceHive.getInstance().getDeviceNotificationService().getDeviceNotifications(id, startTimestamp, endTimestamp).getData();
    }

    public DeviceCommandCallback sendCommand(String command, List<Parameter> parameters) {
        DeviceCommandCallback resultCallback = new DeviceCommandCallback() {

            public void onSuccess(DeviceCommand command) {

            }

            public void onFail(FailureData failureData) {

            }
        };
        DeviceHive.getInstance().getCommandService()
                .sendCommand(id, command, parameters, resultCallback);
        return resultCallback;
    }

    public DHResponse<DeviceNotification> sendNotification(String notification, List<Parameter> parameters) {
        return DeviceHive.getInstance().getDeviceNotificationService().sendNotification(id, notification,
                parameters);
    }

    public void subscribeCommands(CommandFilter commandFilter, DeviceCommandsCallback commandCallback) {
        this.commandCallback = commandCallback;
        DeviceHive.getInstance().getCommandService().pollCommands(id, commandFilter, true, commandCallback);
    }

    public void subscribeNotifications(NotificationFilter notificationFilter, DeviceNotificationsCallback notificationCallback) {
        this.notificationCallback = notificationCallback;
        DeviceHive.getInstance().getDeviceNotificationService().pollNotifications(id, notificationFilter, true, notificationCallback);
    }

    public void unsubscribeCommands(CommandFilter commandFilter) {
        DeviceHive.getInstance().getCommandService().pollCommands(id, commandFilter, true, commandCallback);
    }

    public void unsubscribeNotifications() {
        DeviceHive.getInstance().getDeviceNotificationService().unsubscribeAll();
    }

    public void unsubscribeCommands() {
        DeviceHive.getInstance().getCommandService().unsubscribe();
    }

    public void unsubscribeNotifications(NotificationFilter notificationFilter) {
        DeviceHive.getInstance().getDeviceNotificationService().pollNotifications(id, notificationFilter, true, notificationCallback);
    }
}
