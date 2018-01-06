/*
 *
 *
 *   Device.java
 *
 *   Copyright (C) 2018 DataArt
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

import com.github.devicehive.client.model.CommandFilter;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceCommandsCallback;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.client.model.DeviceNotificationsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.NotificationFilter;
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.rest.api.ApiInfoApi;
import com.github.devicehive.rest.model.ApiInfo;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.api.NotificationWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.repsonse.CommandGetResponse;
import com.github.devicehive.websocket.model.repsonse.CommandInsertResponse;
import com.github.devicehive.websocket.model.repsonse.CommandListResponse;
import com.github.devicehive.websocket.model.repsonse.CommandSubscribeResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationGetResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationListResponse;
import com.github.devicehive.websocket.model.repsonse.NotificationSubscribeResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Device implements DeviceInterface {


    private NotificationWS notificationWS;
    private WebSocketClient wsClient;
    private CommandWS commandWS;
    private String id = null;
    private String name = null;
    private JsonObject data = new JsonObject();
    private Long networkId = null;
    private Boolean isBlocked = false;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        if (data != null) this.data = data;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    private DeviceCommandsCallback commandCallback;
    private DeviceNotificationsCallback notificationCallback;
    public String commandSubscriptionId;
    private CommandListener commandListener = new CommandListener() {
        @Override
        public void onList(CommandListResponse response) {
            commandCallback.onSuccess(DeviceCommand.createListFromRest(response.getCommands()));
        }

        @Override
        public void onGet(CommandGetResponse response) {

        }

        @Override
        public void onInsert(CommandInsertResponse response) {
            commandCallback.onSuccess(Collections.singletonList(DeviceCommand.create(response.getCommand())));
        }

        @Override
        public void onUpdate(ResponseAction response) {

        }

        public void onSubscribe(CommandSubscribeResponse response) {
            commandSubscriptionId = response.getSubscriptionId();
        }

        @Override
        public void onUnsubscribe(ResponseAction response) {

        }

        public void onError(ErrorResponse error) {
            if (error.getCode() == 401) {
                RestHelper.getInstance().authorize(new Callback<JwtAccessToken>() {
                    @Override
                    public void onResponse(Call<JwtAccessToken> call, Response<JwtAccessToken> response) {
                        if (response.isSuccessful()) {
                            TokenHelper.getInstance().getTokenAuth().setAccessToken(response.body().getAccessToken());
                            wsClient.authenticate(TokenHelper.getInstance().getTokenAuth().getAccessToken());
                            subscribeCommands(commandFilter, commandCallback);
                        } else {
                            commandCallback.onFail(FailureData.create(
                                    ErrorResponse.create(response.code(), BaseService.parseErrorMessage(response))));
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtAccessToken> call, Throwable t) {
                        commandCallback.onFail(FailureData.create(ErrorResponse.create(
                                FailureData.NO_CODE, t.getMessage())));
                    }
                });
                return;
            }
            commandCallback.onFail(FailureData.create(error));
        }
    };
    private NotificationListener notificationListener = new NotificationListener() {
        @Override
        public void onList(NotificationListResponse response) {
            notificationCallback.onSuccess(DeviceNotification.createListFromWS(response.getNotifications()));
        }

        @Override
        public void onGet(NotificationGetResponse response) {

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
        public void onUnsubscribe(ResponseAction response) {

        }

        @Override
        public void onError(ErrorResponse error) {
            if (error.getCode() == 401) {
                RestHelper.getInstance().authorize(new Callback<JwtAccessToken>() {
                    @Override
                    public void onResponse(Call<JwtAccessToken> call, Response<JwtAccessToken> response) {
                        if (response.isSuccessful()) {
                            TokenHelper.getInstance().getTokenAuth().setAccessToken(response.body().getAccessToken());
                            wsClient.authenticate(TokenHelper.getInstance().getTokenAuth().getAccessToken());
                            subscribeNotifications(notificationFilter, notificationCallback);
                        } else {
                            notificationCallback.onFail(FailureData.create(
                                    ErrorResponse.create(response.code(), BaseService.parseErrorMessage(response))));
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtAccessToken> call, Throwable t) {
                        notificationCallback.onFail(FailureData.create(
                                ErrorResponse.create(FailureData.NO_CODE, t.getMessage())));
                    }
                });
                return;
            }
            notificationCallback.onFail(FailureData.create(error));
        }
    };
    private String notificationSubscriptionId;
    private CommandFilter commandFilter;
    private NotificationFilter notificationFilter;

    private Device() {
        String wsUrl = DeviceHive.getInstance().getWSUrl();
        if (wsUrl != null && !wsUrl.isEmpty()) {
            init(wsUrl);
        } else {
            DeviceHive.getInstance().getWebSocketUrlFromServer();
            RestHelper.getInstance().getApiClient().clearAuthorizations();
            ApiInfoApi api = RestHelper.getInstance().getApiClient()
                    .createService(ApiInfoApi.class);
            Call<ApiInfo> infoCall = api.getApiInfo();
            try {
                Response<ApiInfo> apiInfoResponse = infoCall.execute();
                if (apiInfoResponse.isSuccessful()) {
                    String webSocketServerUrl = apiInfoResponse.body().getWebSocketServerUrl();
                    DeviceHive.getInstance().serWSUrl(webSocketServerUrl);
                    init(webSocketServerUrl);
                } else {
                    throw new RuntimeException("Could not initialize WS Client for Device:" + id
                            + " with error code: " + apiInfoResponse.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error occurred while creating WS Client for device " + id);
            }
        }
    }

    private void init(String wsUrl) {
        wsClient = new WebSocketClient.Builder().url(wsUrl).build();
        commandWS = wsClient.createCommandWS();
        commandWS.setListener(commandListener);
        notificationWS = wsClient.createNotificationWS();
        notificationWS.setListener(notificationListener);
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
        DeviceHive.getInstance().updateDevice(this);
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
                .sendCommand(id, networkId, command, parameters);
    }

    public DHResponse<DeviceNotification> sendNotification(String notification, List<Parameter> parameters) {
        return DeviceHive.getInstance().getDeviceNotificationService().sendNotification(id, notification,
                parameters);
    }

    public void subscribeCommands(CommandFilter commandFilter, DeviceCommandsCallback commandCallback) {
        this.commandCallback = commandCallback;
        this.commandFilter = commandFilter;
        commandWS.subscribe(commandFilter.getCommandNames(),
                id,
                null,
                commandFilter.getStartTimestamp(),
                commandFilter.getMaxNumber());

    }

    public void subscribeNotifications(NotificationFilter notificationFilter, DeviceNotificationsCallback notificationCallback) {
        this.notificationCallback = notificationCallback;
        this.notificationFilter = notificationFilter;
        notificationWS.subscribe(id, null, notificationFilter.getNotificationNames());
    }

    public void unsubscribeCommands(CommandFilter commandFilter) {
        this.commandFilter = commandFilter;
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
        this.notificationFilter = notificationFilter;
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
