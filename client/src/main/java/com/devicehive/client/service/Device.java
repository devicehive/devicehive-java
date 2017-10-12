package com.devicehive.client.service;

import com.devicehive.client.model.*;
import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.websocket.api.CommandWS;
import com.devicehive.websocket.api.NotificationWS;
import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.listener.NotificationListener;
import com.devicehive.websocket.model.repsonse.*;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Device implements DeviceInterface {


    private final NotificationWS notificationWS;
    private CommandWS commandWS;
    private String id = null;

    private String name = null;

    private JsonStringWrapper data = null;

    private Long networkId = null;

    private Boolean isBlocked = false;
    private DeviceCommandsCallback commandCallback;
    private DeviceNotificationsCallback notificationCallback;
    public String commandSubscriptionId;
    private CommandListener commandListener = new CommandListener() {
        @Override
        public void onList(CommandListResponse response) {
            commandCallback.onSuccess(DeviceCommand.createList(response.getCommands()));
        }

        @Override
        public void onInsert(CommandInsertResponse response) {
            commandCallback.onSuccess(Collections.singletonList(DeviceCommand.create(response.getCommand())));
        }

        public void onSubscribe(CommandSubscribeResponse response) {
            commandSubscriptionId = response.getSubscriptionId();
        }

        public void onError(ErrorResponse error) {
            commandCallback.onFail(FailureData.create(error));
        }
    };
    private NotificationListener notificationListener = new NotificationListener() {
        @Override
        public void onList(NotificationListResponse response) {
            notificationCallback.onSuccess(DeviceNotification.createListFromWS(response.getNotifications()));
        }

        @Override
        public void onInsert(NotificationInsertResponse response) {
            notificationCallback.onSuccess(Collections.singletonList(DeviceNotification.create(response.getNotification())));
        }

        @Override
        public void onSubscribe(NotificationSubscribeResponse response) {
            notificationSubscriptionId = response.getSubscriptionId();
        }

        @Override
        public void onError(ErrorResponse error) {
            notificationCallback.onFail(FailureData.create(error));
        }
    };
    private String notificationSubscriptionId;

    private Device() {
        commandWS = DeviceHive.getInstance().getWsClient().createCommandWS(commandListener);
        notificationWS = DeviceHive.getInstance().getWsClient().createNotificationWS(notificationListener);
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

    public DHResponse<DeviceCommand> sendCommand(String command, List<Parameter> parameters) {
        DHResponse<DeviceCommand> response = DeviceHive.getInstance().getCommandService()
                .sendCommand(id, command, parameters);
        return response;
    }

    public DHResponse<DeviceNotification> sendNotification(String notification, List<Parameter> parameters) {
        return DeviceHive.getInstance().getDeviceNotificationService().sendNotification(id, notification,
                parameters);
    }

    public void subscribeCommands(CommandFilter commandFilter, final DeviceCommandsCallback commandCallback) {
        this.commandCallback = commandCallback;
        commandWS.subscribe(commandFilter.getCommandNames(),
                id,
                null,
                commandFilter.getStartTimestamp(),
                commandFilter.getMaxNumber());

    }

    public void subscribeNotifications(NotificationFilter notificationFilter, DeviceNotificationsCallback notificationCallback) {
        this.notificationCallback = notificationCallback;
        notificationWS.subscribe(id, null, notificationFilter.getNotificationNames());
    }

    public void unsubscribeCommands(CommandFilter commandFilter) {
        commandWS.subscribe(commandFilter.getCommandNames(),
                id,
                null,
                commandFilter.getStartTimestamp(),
                commandFilter.getMaxNumber());
    }

    public void unsubscribeAllCommands() {
        if (commandWS != null && commandSubscriptionId != null) {
            commandWS.unsubscribe(commandSubscriptionId, Collections.singletonList(id));
        }
    }

    public void unsubscribeNotifications() {
        if (notificationWS != null && notificationSubscriptionId != null) {
            notificationWS.unsubscribe(notificationSubscriptionId, Collections.singletonList(id));
        }
    }


    public void unsubscribeNotifications(NotificationFilter notificationFilter) {
        notificationWS.subscribe(null, id, null, notificationFilter.getNotificationNames());
    }
}
