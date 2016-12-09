package com.devicehive.client.api;

import com.devicehive.client.model.ApiInfoVO;
import com.devicehive.client.model.exceptions.HiveException;

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
    ApiInfoVO getInfo() throws HiveException;

    /**
     * Authenticates a client as a user (by login and password). Permissions will be determined by user's role.
     *
     * @param login    login
     * @param password password
     */
    void authenticate(String login, String password) throws HiveException;

    /**
     * Authenticates a client by an access key. Permissions will be determined by the access key permissions.
     *
     * @param key access key
     */
    void authenticate(String key) throws HiveException;

    /**
     * Return new instance of access key API
     *
     * @return access key API
     */
    AccessKeyApi getAccessKeyAPI();

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
