package com.devicehive.client;

import com.devicehive.client.api.MainDeviceHive;
import com.devicehive.client.model.BasicAuth;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.ApiInfoService;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.ApiInfo;
import okhttp3.WebSocketListener;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.List;

public class DeviceHive implements MainDeviceHive {

    static String URL;

    private TokenAuth tokenAuth;
    private BasicAuth basicAuth;

    private JwtTokenApi jwtService;

    public DeviceHive(@Nonnull String url, @Nonnull TokenAuth tokenAuth) {
        DeviceHive.URL = url;
        this.tokenAuth = tokenAuth;
    }

    public DeviceHive(@Nonnull String url, @Nonnull BasicAuth basicAuth) {
        DeviceHive.URL = url;
        this.basicAuth = basicAuth;
    }


    public DHResponse<ApiInfo> getInfo() {
        ApiInfoService service = new ApiInfoService();
        return service.getApiInfo();
    }


    public void getClusterInfo() {

    }

    public String createToken(List<String> actions, String userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) {
        return null;
    }

    public String refreshToken() {
        return null;
    }

    public void getProperty(String name) {

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
