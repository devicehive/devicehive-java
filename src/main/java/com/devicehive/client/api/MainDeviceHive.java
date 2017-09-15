package com.devicehive.client.api;

import com.devicehive.client.model.DHResponse;
import com.devicehive.rest.model.*;
import okhttp3.WebSocketListener;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public interface MainDeviceHive {

    DHResponse<ApiInfo> getInfo() throws IOException;

    DHResponse<ClusterConfig> getClusterInfo();

    DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) throws IOException;

    DHResponse<JwtAccessToken> refreshToken() throws IOException;

    DHResponse<Configuration> getProperty(String name) throws IOException;

    DHResponse<Configuration> setProperty(String name, String value) throws IOException;

    DHResponse<Void> removeProperty(String name) throws IOException;

    void subscribeCommands(List<String> ids, WebSocketListener callback, String commandFilter);

    void subscribeNotifications(List<String> ids, WebSocketListener callback, String nameFilter);

    void unsubscribeCommands(List<String> ids, String commandFilter);

    void unsubscribeNotifications(List<String> ids, String nameFilter);

    void listNetworks(String filter);

    void getNetwork(String id);

    void removeNetwork(String id);

    void createNetwork(String name, String description);

    void listDevices(String filter);

    void removeDevice(String id);

    void getDevice(String id);

    void putDevice(String id, String name);
}
