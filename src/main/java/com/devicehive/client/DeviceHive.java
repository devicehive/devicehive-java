package com.devicehive.client;

import com.devicehive.client.api.MainDeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.*;
import com.devicehive.client.service.Device;
import com.devicehive.client.model.Network;
import com.devicehive.client.service.*;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.*;
import okhttp3.WebSocketListener;
import org.joda.time.DateTime;
import retrofit2.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class DeviceHive implements MainDeviceHive {

    private ConfigurationService configurationService;
    private NetworkService networkService;
    private ApiInfoService apiInfoService;
    private JwtTokenService jwtTokenService;
    private DeviceService deviceService;
    private String url;
    private DeviceCommandService deviceCommandService;
    private DeviceNotificationService notificationService;

    private DeviceHive() {
    }

    private static class InstanceHolder {
        static final DeviceHive INSTANCE = new DeviceHive();
    }

    String getUrl() {
        return url;
    }

    public static DeviceHive getInstance() {
        return DeviceHive.InstanceHolder.INSTANCE;
    }


    public DeviceHive setup(@Nonnull String url, @Nonnull TokenAuth tokenAuth) {
        this.url = url;
        this.setAuth(tokenAuth.getAccessToken(), tokenAuth.getRefreshToken());
        this.createServices();
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
        deviceCommandService = new DeviceCommandService();
        notificationService = new DeviceNotificationService();
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

    public DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) throws IOException {
        return jwtTokenService.createToken(actions, userId, networkIds, deviceIds, expiration);
    }

    public DHResponse<JwtAccessToken> refreshToken() throws IOException {
        return jwtTokenService.getRefreshToken();
    }

    public DHResponse<Configuration> getProperty(String name) throws IOException {
        return configurationService.getProperty(name);
    }

    public DHResponse<Configuration> setProperty(String name, String value) throws IOException {
        return configurationService.setProperty(name, value);
    }

    public DHResponse<Void> removeProperty(String name) throws IOException {
        return configurationService.removeProperty(name);
    }

    public void subscribeCommands(List<String> ids, WebSocketListener callback, CommandFilter commandFilter) {

    }

    public void subscribeNotifications(List<String> ids, WebSocketListener callback, NotificationFilter notificationFilter) {

    }

    public void unsubscribeCommands(List<String> ids, CommandFilter commandFilter) {

    }

    public void unsubscribeNotifications(List<String> ids, NotificationFilter notificationFilter) {

    }

    public DHResponse<List<Network>> listNetworks(NetworkFilter filter) throws IOException {
        return networkService.listNetworks(filter);
    }

    public DHResponse<NetworkVO> getNetwork(long id) throws IOException {
        return networkService.getNetwork(id);
    }

    public DHResponse<Void> removeNetwork(long id) throws IOException {
        return networkService.removeNetwork(id);
    }

    public DHResponse<Network> createNetwork(String name, String description) throws IOException {
        return networkService.createNetwork(name, description);
    }

    public DHResponse<List<Device>> listDevices(DeviceFilter filter) {
        return null;
    }

    public DHResponse<Void> removeDevice(String id) {
        return null;
    }

    @Nullable
    public Device getDevice(String id) {
        return deviceService.getDevice(id);
    }

    public DHResponse<Void> putDevice(String id, String name) {
        return deviceService.createDevice(id, name);
    }

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public DeviceCommandService getDeviceCommandService() {
        return deviceCommandService;
    }

    public DeviceNotificationService getDeviceNotificationService() {
        return notificationService;
    }
}
