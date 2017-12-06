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
import com.github.devicehive.client.model.CommandFilter;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceCommandsCallback;
import com.github.devicehive.client.model.DeviceFilter;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.client.model.DeviceNotificationsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.NetworkFilter;
import com.github.devicehive.client.model.NotificationFilter;
import com.github.devicehive.client.model.UserFilter;
import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.ApiInfoApi;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.ApiInfo;
import com.github.devicehive.rest.model.ClusterConfig;
import com.github.devicehive.rest.model.Configuration;
import com.github.devicehive.rest.model.JsonStringWrapper;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtRequest;
import com.github.devicehive.rest.model.JwtToken;
import com.github.devicehive.rest.model.NetworkVO;
import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.StatusEnum;
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

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private List<String> notificationsIds = new ArrayList<>();
    private NotificationFilter notificationFilter;
    private List<String> commandsIds = new ArrayList<>();
    private CommandFilter commandFilter;
    private Call<ApiInfo> infoCall;

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


    public DeviceHive init(String url, String refreshToken) {
        return init(url, refreshToken, null);
    }

    public DeviceHive init(String url, String refreshToken, String accessToken) {
        return init(url, null, refreshToken, accessToken);
    }

    public DeviceHive init(String url, String wsUrl, String refreshToken, String accessToken) {
        if (url == null || url.length() == 0) {
            throw new NullPointerException("Server url cannot be null or empty");
        }
        this.url = url;
        if (refreshToken == null || refreshToken.length() == 0) {
            throw new NullPointerException("Refresh Token cannot be null or empty");
        }
        if (wsUrl != null && wsUrl.length() > 0) {
            this.wsUrl = wsUrl;
            this.createWsServices();
        }
        this.setAuth(accessToken, refreshToken);
        this.createServices();
        RestHelper.getInstance().authorize();
        return this;
    }

    private void setAuth(String accessToken, String refreshToken) {
        TokenHelper.getInstance()
                .getTokenAuth()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
    }

    public void enableDebug(boolean enable) {
        RestHelper.getInstance().enableDebug(enable);
        createServices();
    }

    public void enableDebug(boolean enable, HttpLoggingInterceptor.Level level) {
        RestHelper.getInstance().enableDebug(enable, level);
        createServices();
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
        wsClient = new WebSocketClient.Builder().url(wsUrl).build();
    }

    private void createWsServices() {
        createWSClient();
        notificationWS = wsClient.createNotificationWS();
        notificationWS.setListener(new NotificationListener() {
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

            @Override
            public void onError(ErrorResponse error) {
                if (error.getCode() == 401) {
                    RestHelper.getInstance().authorize(new Callback<JwtAccessToken>() {
                        @Override
                        public void onResponse(Call<JwtAccessToken> call, Response<JwtAccessToken> response) {
                            if (response.isSuccessful()) {
                                TokenHelper.getInstance().getTokenAuth().setAccessToken(response.body().getAccessToken());
                                wsClient.authenticate(TokenHelper.getInstance().getTokenAuth().getAccessToken());
                                subscribeNotifications(notificationsIds, notificationFilter, notificationsCallback);
                            } else {
                                notificationsCallback.onFail(FailureData.create(
                                        ErrorResponse.create(response.code(), BaseService.parseErrorMessage(response))));
                            }
                        }

                        @Override
                        public void onFailure(Call<JwtAccessToken> call, Throwable t) {
                            notificationsCallback.onFail(FailureData.create(
                                    ErrorResponse.create(FailureData.NO_CODE, t.getMessage())));
                        }
                    });
                    return;
                }
                notificationsCallback.onFail(FailureData.create(error));
            }

        });
        commandWS = wsClient.createCommandWS();
        commandWS.setListener(new CommandListener() {
            @Override
            public void onList(CommandListResponse response) {
                commandsCallback.onSuccess(DeviceCommand.createListFromRest(response.getCommands()));
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
                if (error.getCode() == 401) {
                    RestHelper.getInstance().authorize(new Callback<JwtAccessToken>() {
                        @Override
                        public void onResponse(Call<JwtAccessToken> call, Response<JwtAccessToken> response) {
                            if (response.isSuccessful()) {
                                TokenHelper.getInstance().getTokenAuth().setAccessToken(response.body().getAccessToken());
                                wsClient.authenticate(TokenHelper.getInstance().getTokenAuth().getAccessToken());
                                subscribeCommands(commandsIds, commandFilter, commandsCallback);
                            } else {
                                commandsCallback.onFail(FailureData.create(
                                        ErrorResponse.create(response.code(), BaseService.parseErrorMessage(response))));
                            }
                        }

                        @Override
                        public void onFailure(Call<JwtAccessToken> call, Throwable t) {
                            commandsCallback.onFail(FailureData.create(
                                    ErrorResponse.create(FailureData.NO_CODE, t.getMessage())));
                        }
                    });
                    return;
                }
                commandsCallback.onFail(FailureData.create(error));
            }
        });
    }

    public void login(String username, String password) throws IOException {
        checkWsInitialized();
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
        checkWsInitialized();
        return apiInfoService.getApiInfo();
    }

    public DHResponse<ClusterConfig> getClusterInfo() {
        checkWsInitialized();
        return apiInfoService.getClusterConfig();
    }

    public DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) {
        checkWsInitialized();
        return jwtTokenService.createToken(actions, userId, networkIds, deviceIds, expiration);
    }

    public DHResponse<JwtAccessToken> refreshToken() {
        checkWsInitialized();
        return jwtTokenService.getRefreshToken();
    }

    public DHResponse<Configuration> getProperty(String name) {
        checkWsInitialized();
        return configurationService.getProperty(name);
    }

    public DHResponse<Configuration> setProperty(String name, String value) {
        checkWsInitialized();
        return configurationService.setProperty(name, value);
    }

    public DHResponse<Void> removeProperty(String name) {
        checkWsInitialized();
        return configurationService.removeProperty(name);
    }

    public void subscribeCommands(final List<String> ids, final CommandFilter commandFilter, final DeviceCommandsCallback commandsCallback) {
        checkWsInitialized();

        this.commandsCallback = commandsCallback;
        this.commandFilter = commandFilter;
        commandsIds.clear();
        commandsIds.addAll(ids);
        commandWS.subscribe(commandFilter.getCommandNames(), null,
                ids, commandFilter.getStartTimestamp(), commandFilter.getMaxNumber());
    }

    public void subscribeNotifications(List<String> ids, NotificationFilter notificationFilter, DeviceNotificationsCallback notificationsCallback) {
        checkWsInitialized();

        this.notificationsCallback = notificationsCallback;
        this.notificationFilter = notificationFilter;
        notificationsIds.clear();
        notificationsIds.addAll(ids);
        notificationWS.subscribe(null, ids, notificationFilter.getNotificationNames());
    }

    public void unsubscribeCommands(List<String> ids, CommandFilter commandFilter) {
        checkWsInitialized();

        this.commandFilter = commandFilter;
        commandsIds.clear();
        commandsIds.addAll(ids);
        commandWS.subscribe(commandFilter.getCommandNames(), null,
                ids, commandFilter.getStartTimestamp(), commandFilter.getMaxNumber());
    }

    public void unsubscribeNotifications(List<String> ids, NotificationFilter notificationFilter) {
        checkWsInitialized();
        this.notificationFilter = notificationFilter;
        notificationsIds.clear();
        notificationsIds.addAll(ids);
        notificationWS.subscribe(null, ids, notificationFilter.getNotificationNames());
    }

    public void unsubscribeAllCommands() {
        checkWsInitialized();
        if (commandWS != null && commandSubscriptionId != null) {
            checkWsInitialized();
            commandWS.unsubscribe(commandSubscriptionId, null);
        }
    }

    public void unsubscribeAllNotifications() {
        checkWsInitialized();
        if (notificationWS != null && notificationSubscriptionId != null) {
            checkWsInitialized();
            notificationWS.unsubscribe(notificationSubscriptionId, null);
        }
    }

    public DHResponse<List<Network>> listNetworks(NetworkFilter filter) {
        checkWsInitialized();
        return networkService.listNetworks(filter);
    }

    public DHResponse<NetworkVO> getNetwork(long id) {
        checkWsInitialized();
        return networkService.getNetwork(id);
    }

    public DHResponse<Void> removeNetwork(long id) {
        checkWsInitialized();
        return networkService.removeNetwork(id);
    }

    public DHResponse<Network> createNetwork(String name, String description) {
        checkWsInitialized();
        return networkService.createNetwork(name, description);
    }

    public DHResponse<List<Device>> listDevices(DeviceFilter filter) {
        checkWsInitialized();
        return deviceService.getDevices(filter);
    }

    public DHResponse<Void> removeDevice(String id) {
        checkWsInitialized();
        return deviceService.removeDevice(id);
    }

    public DHResponse<Device> getDevice(String id) {
        checkWsInitialized();
        return deviceService.getDevice(id);
    }

    public DHResponse<User> getCurrentUser() {
        checkWsInitialized();
        return userService.getCurrentUser();
    }

    public DHResponse<List<User>> getUsers(UserFilter filter) {
        checkWsInitialized();
        return userService.listUsers(filter);
    }

    public DHResponse<User> createUser(String login, String password, RoleEnum role, StatusEnum status, JsonStringWrapper data) {
        checkWsInitialized();
        return userService.createUser(login, password, role, status, data);
    }

    public DHResponse<Void> removeUser(long id) {
        checkWsInitialized();
        return userService.removeUser(id);
    }

    //GET SERVICES
    public DHResponse<Void> putDevice(String id, String name) {
        checkWsInitialized();
        return deviceService.createDevice(id, name);
    }

    NetworkService getNetworkService() {
        checkWsInitialized();
        return networkService;
    }

    DeviceCommandService getCommandService() {
        checkWsInitialized();
        return commandService;
    }

    UserService getUserService() {
        checkWsInitialized();
        return userService;
    }

    DeviceNotificationService getDeviceNotificationService() {
        checkWsInitialized();
        return notificationService;
    }

    private void checkWsInitialized() {
        if (wsUrl != null) {
            return;
        }
        if (infoCall != null && infoCall.isExecuted()) {
            return;
        }
        ApiClient apiClient = new ApiClient(url);
        apiClient.clearAuthorizations();
        ApiInfoApi api = apiClient.createService(ApiInfoApi.class);
        infoCall = api.getApiInfo();

        try {
            Response<ApiInfo> apiInfoResponse = infoCall.execute();
            if (apiInfoResponse.isSuccessful()) {
                wsUrl = apiInfoResponse.body().getWebSocketServerUrl();
                createWsServices();
            } else {
                throw new RuntimeException("Couldn't initialize WS client. Error code: "
                        + apiInfoResponse.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
