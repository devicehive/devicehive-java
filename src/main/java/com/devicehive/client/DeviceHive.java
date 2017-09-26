package com.devicehive.client;

import com.devicehive.client.api.MainDeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.NetworkFilter;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.ApiInfoService;
import com.devicehive.client.service.ConfigurationService;
import com.devicehive.client.service.JwtTokenService;
import com.devicehive.client.service.NetworkService;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.*;
import okhttp3.WebSocketListener;
import org.joda.time.DateTime;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class DeviceHive implements MainDeviceHive {

    static String URL;
    private ConfigurationService configurationService;
    private NetworkService networkService;
    private ApiInfoService apiInfoService;
    private JwtTokenService jwtTokenService;

    public TokenAuth getTokenAuthService() {
        return jwtTokenService.getTokenAuth();
    }

    public TokenAuth getTokenConfigurationService() {
        return configurationService.getTokenAuth();
    }


    public DeviceHive(@Nonnull String url, @Nonnull TokenAuth tokenAuth) {
        DeviceHive.URL = url;
        TokenHelper.getInstance().getTokenAuth().setAccessToken(tokenAuth.getAccessToken());
        TokenHelper.getInstance().getTokenAuth().setRefreshToken(tokenAuth.getRefreshToken());

        apiInfoService = new ApiInfoService();
        jwtTokenService = new JwtTokenService();
        configurationService = new ConfigurationService();
        networkService = new NetworkService();
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

    public void subscribeCommands(List<String> ids, WebSocketListener callback, String commandFilter) {

    }

    public void subscribeNotifications(List<String> ids, WebSocketListener callback, String nameFilter) {

    }

    public void unsubscribeCommands(List<String> ids, String commandFilter) {

    }

    public void unsubscribeNotifications(List<String> ids, String nameFilter) {

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

    public DHResponse<NetworkId> createNetwork(String name, String description) throws IOException {
        return networkService.createNetwork(name, description);
    }

    public void listDevices(String filter) {

    }

    public void removeDevice(String id) {

    }

    public void getDevice(String id) {

    }

    public void putDevice(String id, String name) {

    }

}
