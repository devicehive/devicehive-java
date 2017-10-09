package com.devicehive.client;

import com.devicehive.client.api.MainDeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.*;
import com.devicehive.client.model.Network;
import com.devicehive.client.service.*;
import com.devicehive.client.service.Device;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.*;
import com.devicehive.websocket.api.CommandWS;
import com.devicehive.websocket.api.NotificationWS;
import com.devicehive.websocket.api.WebSocketClient;
import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.listener.NotificationListener;
import com.devicehive.websocket.model.repsonse.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class DeviceHive implements MainDeviceHive {

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

    private CommandListener commandListener = new CommandListener() {
        @Override
        public void onList(CommandListResponse response) {

        }

    };

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

    public WebSocketClient getWsClient() {
        return WSHelper.getInstance().getWebSocketClient();
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
        System.out.println(wsUrl);
        this.setAuth(tokenAuth.getAccessToken(), tokenAuth.getRefreshToken());
        this.createServices();
        this.createWsServices();
        return this;
    }

    private String createWSUrl(String httpUrl) {
        String wsUrl = httpUrl.replace("http", "ws")
                .replace("rest", "websocket");
        if (wsUrl.endsWith("/")) {
            wsUrl = wsUrl.substring(0,wsUrl.length() - 1);
        }
        return wsUrl;
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
    }

    private DeviceHive createWsServices() {
        notificationWS = DeviceHive.getInstance().getWsClient().createNotificationWS(new NotificationListener() {
            @Override
            public void onError(ErrorResponse error) {
            }

            @Override
            public void onList(NotificationListResponse response) {

            }

            @Override
            public void onSubscribe(NotificationSubscribeResponse response) {
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {
            }

            @Override
            public void onInsert(NotificationInsertResponse response) {
            }
        });
        commandWS = DeviceHive.getInstance().getWsClient().createCommandWS(new CommandListener() {
            @Override
            public void onInsert(CommandInsertResponse response) {
            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {
            }

            @Override
            public void onUnsubscribe(ResponseAction response) {
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        return this;
    }


    public TokenAuth getTokenAuthService() {
        return jwtTokenService.getTokenAuth();
    }

    public TokenAuth getTokenConfigurationService() {
        return configurationService.getTokenAuth();
    }

    public void login(String username, String password) throws IOException {
        JwtRequest body = new JwtRequest();
        body.setLogin(username);
        body.setPassword(password);

        JwtTokenApi jwtTokenApi = RestHelper.getInstance().getApiClient()
                .createService(JwtTokenApi.class);
        Response<JwtToken> response = jwtTokenApi.login(body).execute();
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
        String deviceIds = StringUtils.join(ids, ",");
        this.commandsCallback = commandsCallback;
//        commandService.pollManyCommands(deviceIds, commandFilter, true, commandsCallback);

    }

    public void subscribeNotifications(List<String> ids, NotificationFilter notificationFilter, DeviceNotificationsCallback notificationsCallback) {
        String deviceIds = StringUtils.join(ids, ",");
        this.notificationsCallback = notificationsCallback;

        notificationService.pollManyNotifications(deviceIds, notificationFilter, true, notificationsCallback);
    }

    public void unsubscribeCommands(List<String> ids, CommandFilter commandFilter) {
        String deviceIds = StringUtils.join(ids, ",");
//        commandService.pollManyCommands(deviceIds, commandFilter, true, commandsCallback);
    }

    public void unsubscribeNotifications(List<String> ids, NotificationFilter notificationFilter) {
        String deviceIds = StringUtils.join(ids, ",");
        notificationService.pollManyNotifications(deviceIds, notificationFilter, true, notificationsCallback);
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

    public DHResponse<Void> putDevice(String id, String name) {
        return deviceService.createDevice(id, name);
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public DeviceCommandService getCommandService() {
        return commandService;
    }

    public DeviceNotificationService getDeviceNotificationService() {
        return notificationService;
    }
}
