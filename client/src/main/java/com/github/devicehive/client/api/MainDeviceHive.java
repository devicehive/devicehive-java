/*
 *
 *
 *   MainDeviceHive.java
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

package com.github.devicehive.client.api;

import com.github.devicehive.rest.model.*;
import com.github.devicehive.client.model.*;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.Network;
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

    DHResponse<List<com.github.devicehive.client.service.Network>> listNetworks(NetworkFilter filter) ;

    DHResponse<NetworkVO> getNetwork(long id) ;

    DHResponse<Void> removeNetwork(long id) ;

    DHResponse<Network> createNetwork(String name, String description) ;

    DHResponse<List<Device>> listDevices(DeviceFilter filter);

    DHResponse<Void> removeDevice(String id);

    DHResponse<Device> getDevice(String id) ;

    DHResponse<Void> putDevice(String id, String name) ;
}
