/*
 *
 *
 *   DeviceHive.java
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

import com.github.devicehive.client.api.MainDeviceHive;
import com.github.devicehive.client.callback.ResponseCallback;
import com.github.devicehive.client.exceptions.IncorrectUrlException;
import com.github.devicehive.client.model.*;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.*;
import com.github.devicehive.rest.model.User.RoleEnum;
import com.github.devicehive.rest.model.User.StatusEnum;
import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.api.NotificationWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.listener.NotificationListener;
import com.github.devicehive.websocket.model.repsonse.*;
import org.joda.time.DateTime;
import retrofit2.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class DeviceHive implements MainDeviceHive {

    private static final String WEBSOCKET_PATH = "/api/websocket";
    private static final String HTTP = "http";
    private static final String WS = "ws";
    private ConfigurationService configurationService;
    private NetworkService networkService;
    private ApiInfoService apiInfoService;
    private JwtTokenService jwtTokenService;
    private DeviceService deviceService;
    private String url;
    private DeviceCommandService commandService;
    private DeviceNotificationService notificationService;
    private DeviceNotificationsCallback notificationsCallback;
    private DeviceCommandsCallback commandsCallback;
    private String wsUrl;
    private NotificationWS notificationWS;
    private CommandWS commandWS;
    private UserService userService;
    private WebSocketClient wsClient;
    private String commandSubscriptionId;
    private String notificationSubscriptionId;

    private DeviceHive() {
    }

    private static class InstanceHolder {
        static final DeviceHive INSTANCE = new DeviceHive();
    }

    String getUrl() {
        return url;
    }

    String getWSUrl() {
        return wsUrl;
    }

    public static DeviceHive getInstance() {
        return DeviceHive.InstanceHolder.INSTANCE;
    }


    public DeviceHive init(String url, TokenAuth tokenAuth) {
        if (url == null || url.length() == 0) {
            throw new NullPointerException("Server url cannot be empty");
        }
        this.url = url;
        this.wsUrl = createWSUrl(url);
        this.setAuth(tokenAuth.getAccessToken(), tokenAuth.getRefreshToken());
        this.createServices();
        this.createWsServices();
        return this;
    }

    private String createWSUrl(String httpUrl) {
        try {
            URI uri = new URI(httpUrl);
            return new URI(
                    uri.getScheme().replace(HTTP, WS),
                    uri.getHost(),
                    WEBSOCKET_PATH,
                    null).toString();
        } catch (URISyntaxException e) {
            throw new IncorrectUrlException(httpUrl);
        }

    }

    public DeviceHive init(String url, String wsUrl, TokenAuth tokenAuth) {
        if (url == null || url.length() == 0) {
            throw new NullPointerException("Server url cannot be empty");
        }
        this.url = url;
        if (wsUrl == null || wsUrl.length() == 0) {
            throw new NullPointerException("Server ws url cannot be empty");
        }
        this.wsUrl = wsUrl;
        this.setAuth(tokenAuth.getAccessToken(), tokenAuth.getRefreshToken());
        this.createServices();
        this.createWsServices();
        return this;
    }

    private void setAuth(String accessToken, String refreshToken) {
        TokenHelper.getInstance()
                .getTokenAuth()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
    }

    private void createServices() {
        apiInfoService = new ApiInfoService();
        jwtTokenService = new JwtTokenService();
        configurationService = new ConfigurationService();
        networkService = new NetworkService();
        deviceService = new DeviceService();
        commandService = new DeviceCommandService();
        notificationService = new DeviceNotificationService();
        userService = new UserService();
    }

    private void createWSClient() {
        wsClient = new WebSocketClient.Builder().url(wsUrl)
                .refreshToken(TokenHelper.getInstance().getTokenAuth().getRefreshToken())
                .token(TokenHelper.getInstance().getTokenAuth().getAccessToken())
                .build();
    }

    private void createWsServices() {
        createWSClient();
        notificationWS = wsClient.createNotificationWS(new NotificationListener() {
            @Override
            public void onError(ErrorResponse error) {
                notificationsCallback.onFail(FailureData.create(error));
            }

            @Override
            public void onList(NotificationListResponse response) {
                notificationsCallback.onSuccess(DeviceNotification.createListFromWS(response.getNotifications()));
            }

            @Override
            public void onGet(NotificationGetResponse response) {

            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {
                notificationSubscriptionId = response.getSubscriptionId();
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {
                notificationSubscriptionId = null;
            }

            @Override
            public void onInsert(NotificationInsertResponse response) {
                notificationsCallback.onSuccess(Collections.singletonList(DeviceNotification.create(response.getNotification())));
            }
        });
        commandWS = wsClient.createCommandWS(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {
                commandsCallback.onSuccess(DeviceCommand.createList(response.getCommands()));
            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {
                commandSubscriptionId = response.getSubscriptionId();
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {
                commandSubscriptionId = null;
            }

            @Override
            public void onInsert(CommandInsertResponse response) {
                commandsCallback.onSuccess(Collections.singletonList(DeviceCommand.create(response.getCommand())));
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                commandsCallback.onFail(FailureData.create(error));
            }
        });
    }

    public void login(String username, String password) throws IOException {
        JwtRequest body = new JwtRequest();
        body.setLogin(username);
        body.setPassword(password);

        AuthApi authApi = RestHelper.getInstance().getApiClient()
                .createService(AuthApi.class);
        Response<JwtToken> response = authApi.login(body).execute();
        if (response.isSuccessful()) {
            JwtToken token = response.body();
            TokenHelper.getInstance().getTokenAuth().setAccessToken(token.getAccessToken());
            TokenHelper.getInstance().getTokenAuth().setRefreshToken(token.getRefreshToken());
        }
    }

    public DHResponse<ApiInfo> getInfo() {
        return apiInfoService.getApiInfo();
    }

    public void getInfo(ResponseCallback<ApiInfo> callback) {
        apiInfoService.getApiInfo(callback);
    }


    public DHResponse<ClusterConfig> getClusterInfo() {
        return apiInfoService.getClusterConfig();
    }

    public void getClusterInfo(ResponseCallback<ClusterConfig> callback) {
        apiInfoService.getClusterConfig(callback);
    }

    public DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) {
        return jwtTokenService.createToken(actions, userId, networkIds, deviceIds, expiration);
    }

    public DHResponse<JwtAccessToken> refreshToken() {
        return jwtTokenService.getRefreshToken();
    }

    public DHResponse<Configuration> getProperty(String name) {
        return configurationService.getProperty(name);
    }

    public DHResponse<Configuration> setProperty(String name, String value) {
        return configurationService.setProperty(name, value);
    }

    public DHResponse<Void> removeProperty(String name) {
        return configurationService.removeProperty(name);
    }

    public void subscribeCommands(List<String> ids, CommandFilter commandFilter, DeviceCommandsCallback commandsCallback) {
        this.commandsCallback = commandsCallback;
        commandWS.subscribe(commandFilter.getCommandNames(), null,
                ids, commandFilter.getStartTimestamp(), commandFilter.getMaxNumber());
    }

    public void subscribeNotifications(List<String> ids, NotificationFilter notificationFilter, DeviceNotificationsCallback notificationsCallback) {
        this.notificationsCallback = notificationsCallback;
        notificationWS.subscribe(null, ids, notificationFilter.getNotificationNames());
    }

    public void unsubscribeCommands(List<String> ids, CommandFilter commandFilter) {
        commandWS.subscribe(commandFilter.getCommandNames(), null,
                ids, commandFilter.getStartTimestamp(), commandFilter.getMaxNumber());
    }

    public void unsubscribeNotifications(List<String> ids, NotificationFilter notificationFilter) {
        notificationWS.subscribe(null, ids, notificationFilter.getNotificationNames());
    }

    public void unsubscribeAllCommands() {
        if (commandWS != null && commandSubscriptionId != null) {
            commandWS.unsubscribe(commandSubscriptionId, null);
        }
    }

    public void unsubscribeAllNotifications() {
        if (notificationWS != null && notificationSubscriptionId != null) {
            notificationWS.unsubscribe(notificationSubscriptionId, null);
        }
    }

    public DHResponse<List<Network>> listNetworks(NetworkFilter filter) {
        return networkService.listNetworks(filter);
    }

    public DHResponse<NetworkVO> getNetwork(long id) {
        return networkService.getNetwork(id);
    }

    public DHResponse<Void> removeNetwork(long id) {
        return networkService.removeNetwork(id);
    }

    public DHResponse<Network> createNetwork(String name, String description) {
        return networkService.createNetwork(name, description);
    }

    public DHResponse<List<Device>> listDevices(DeviceFilter filter) {
        return deviceService.getDevices(filter);
    }

    public DHResponse<Void> removeDevice(String id) {
        return deviceService.removeDevice(id);
    }

    public Device getDevice(String id) {
        return deviceService.getDevice(id);
    }

    public DHResponse<User> getCurrentUser() {
        return userService.getCurrentUser();
    }

    public DHResponse<List<User>> getUsers(UserFilter filter) {
        return userService.listUsers(filter);
    }

    public DHResponse<User> createUser(String login, String password, RoleEnum role, StatusEnum status, JsonStringWrapper data) {
        return userService.createUser(login, password, role, status, data);
    }

    public DHResponse<Void> removeUser(long id) {
        return userService.removeUser(id);
    }

    //GET SERVICES
    public DHResponse<Void> putDevice(String id, String name) {
        return deviceService.createDevice(id, name);
    }

    NetworkService getNetworkService() {
        return networkService;
    }

    DeviceCommandService getCommandService() {
        return commandService;
    }

    UserService getUserService() {
        return userService;
    }

    DeviceNotificationService getDeviceNotificationService() {
        return notificationService;
    }
}
