/*
 *
 *
 *   Device.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.client.service;

import com.github.devicehive.rest.model.JsonStringWrapper;
import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.api.NotificationWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.repsonse.*;
import com.github.devicehive.client.model.*;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Device implements DeviceInterface {


    private final NotificationWS notificationWS;
    private final WebSocketClient wsClient;
    private CommandWS commandWS;
    @Getter
    private String id = null;
    @Getter
    @Setter
    private String name = null;
    @Getter
    @Setter
    private JsonStringWrapper data = null;
    @Getter
    @Setter
    private Long networkId = null;
    @Getter
    @Setter
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
        wsClient = new WebSocketClient.Builder().url(DeviceHive.getInstance().getWSUrl())
                .refreshToken(TokenHelper.getInstance().getTokenAuth().getRefreshToken())
                .token(TokenHelper.getInstance().getTokenAuth().getAccessToken())
                .build();
        commandWS = wsClient.createCommandWS(commandListener);
        notificationWS = wsClient.createNotificationWS(notificationListener);
    }

    static Device create(com.github.devicehive.rest.model.Device device) {
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

    static List<Device> list(List<com.github.devicehive.rest.model.Device> devices) {
        List<Device> list = new ArrayList<Device>();
        if (devices == null) {
            return Collections.emptyList();
        }
        for (com.github.devicehive.rest.model.Device device :
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
        return DeviceHive.getInstance().getCommandService()
                .sendCommand(id, command, parameters);
    }

    public DHResponse<DeviceNotification> sendNotification(String notification, List<Parameter> parameters) {
        return DeviceHive.getInstance().getDeviceNotificationService().sendNotification(id, notification,
                parameters);
    }

    public void subscribeCommands(CommandFilter commandFilter, DeviceCommandsCallback commandCallback) {
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

    public void unsubscribeAllNotifications() {
        if (notificationWS != null && notificationSubscriptionId != null) {
            notificationWS.unsubscribe(notificationSubscriptionId, Collections.singletonList(id));
        }
    }


    public void unsubscribeNotifications(NotificationFilter notificationFilter) {
        notificationWS.subscribe(null, id, null, notificationFilter.getNotificationNames());
    }

    @Override
    public String toString() {
        return "{\n\"Device\":{\n"
                + "\"id\":\"" + id + "\""
                + ",\n \"name\":\"" + name + "\""
                + ",\n \"data\":" + data
                + ",\n \"networkId\":\"" + networkId + "\""
                + ",\n \"isBlocked\":\"" + isBlocked + "\""
                + "}\n}";
    }
}
