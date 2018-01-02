/*
 *
 *
 *   HiveClient.java
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

package com.github.devicehive.rest.api;

/**
 * A Device Hive client that provides access to a number of controllers each of which serves as a proxy between the
 * client code and the corresponding Device Hive RESTful API resource. A client needs to be authenticated in order to use
 * controllers methods. For more details on the controllers and methods they provide see the
 * <a href="http://devicehive.com/restful/#Reference">Device Hive RESTful API Reference</a>.
 */
public interface HiveClient extends AutoCloseable {

    /**
     * Requests API information.
     *
     * @return API info
     */
    com.github.devicehive.rest.model.ApiInfo getInfo() throws com.github.devicehive.rest.model.exceptions.HiveException;

    /**
     * Authenticates a client as a user (by login and password). Permissions will be determined by user's role.
     *
     * @param login    login
     * @param password password
     */
    void authenticate(String login, String password) throws com.github.devicehive.rest.model.exceptions.HiveException;

    /**
     * Authenticates a client by an access key. Permissions will be determined by the access key permissions.
     *
     * @param key access key
     */
    void authenticate(String key) throws com.github.devicehive.rest.model.exceptions.HiveException;

    /**
     * Return new instance of command API
     *
     * @return command API
     */
    DeviceCommandApi getCommandsAPI();

    /**
     * Return new instance of device API
     *
     * @return device API
     */
    DeviceApi getDeviceAPI();

    /**
     * Return new instance of network API
     *
     * @return network API
     */
    NetworkApi getNetworkAPI();

    /**
     * Return new instance of notification API
     *
     * @return notification API
     */
    DeviceNotificationApi getNotificationsAPI();

    /**
     * Return new instance of user API.
     *
     * @return user API
     */
    UserApi getUserAPI();
    /**
     * Disconnects the client from the server.
     */
    void close();

}
