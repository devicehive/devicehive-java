package com.devicehive.client.api;

import com.devicehive.client.model.*;
import com.devicehive.client.model.Network;
import com.devicehive.client.service.Device;
import com.devicehive.rest.model.*;
import org.joda.time.DateTime;

import java.util.List;

public interface MainDeviceHive {

    DHResponse<ApiInfo> getInfo() ;

    DHResponse<ClusterConfig> getClusterInfo();

    DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) ;

    DHResponse<JwtAccessToken> refreshToken() ;

    DHResponse<Configuration> getProperty(String name) ;

    DHResponse<Configuration> setProperty(String name, String value) ;

    DHResponse<Void> removeProperty(String name) ;

    void subscribeCommands(List<String> ids, CommandFilter commandFilter, DeviceCommandsCallback commandsCallback);

    void subscribeNotifications(List<String> ids, NotificationFilter notificationFilter, DeviceNotificationsCallback notificationsCallback);

    void unsubscribeCommands(List<String> ids, CommandFilter commandFilter);

    void unsubscribeNotifications(List<String> ids, NotificationFilter notificationFilter);

    DHResponse<List<Network>> listNetworks(NetworkFilter filter) ;

    DHResponse<NetworkVO> getNetwork(long id) ;

    DHResponse<Void> removeNetwork(long id) ;

    DHResponse<Network> createNetwork(String name, String description) ;

    DHResponse<List<Device>> listDevices(DeviceFilter filter);

    DHResponse<Void> removeDevice(String id);

    Device getDevice(String id) ;

    DHResponse<Void> putDevice(String id, String name) ;
}
