package com.devicehive.client;

import com.devicehive.client.api.MainDeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.ApiInfoService;
import com.devicehive.client.service.ConfigurationService;
import com.devicehive.client.service.JwtTokenService;
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
    private final ConfigurationService configurationService;
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
        ApiInfoService service = new ApiInfoService();
        return service.getApiInfo();
    }

    public void getInfo(ResponseCallback<ApiInfo> callback) {
        ApiInfoService service = new ApiInfoService();
        service.getApiInfo(callback);
    }


    public DHResponse<ClusterConfig> getClusterInfo() {
        ApiInfoService service = new ApiInfoService();
        return service.getClusterConfig();
    }

    public void getClusterInfo(ResponseCallback<ClusterConfig> callback) {
        ApiInfoService service = new ApiInfoService();
        service.getClusterConfig(callback);
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

    public void setProperty(String name, String value) {

    }

    public void removeProperty(String name) {

    }

    public void subscribeCommands(List<String> ids, WebSocketListener callback, String commandFilter) {

    }

    public void subscribeNotifications(List<String> ids, WebSocketListener callback, String nameFilter) {

    }

    public void unsubscribeCommands(List<String> ids, String commandFilter) {

    }

    public void unsubscribeNotifications(List<String> ids, String nameFilter) {

    }

    public void listNetworks(String filter) {

    }

    public void getNetwork(String id) {

    }

    public void removeNetwork(String id) {

    }

    public void createNetwork(String name, String description) {

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
