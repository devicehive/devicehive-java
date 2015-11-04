package com.devicehive.client;


import com.devicehive.client.model.ApiInfo;
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
    ApiInfo getInfo() throws HiveException;

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
     * Returns a new instance of access key controller.
     *
     * @return access key controller
     */
    AccessKeyController getAccessKeyController();

    /**
     * Returns a new instance of command controller.
     *
     * @return command controller
     */
    CommandsController getCommandsController();

    /**
     * Returns a new instance of device controller.
     *
     * @return device controller
     */
    DeviceController getDeviceController();

    /**
     * Returns new instance of network controller.
     *
     * @return network controller
     */
    NetworkController getNetworkController();

    /**
     * Returns a new instance of notification controller.
     *
     * @return notification controller
     */
    NotificationsController getNotificationsController();

    /**
     * Returns a new instance of user controller.
     *
     * @return user controller
     */
    UserController getUserController();

    /**
     * Returns a new instance of OAuth client controller.
     *
     * @return OAuth controller
     */
    OAuthClientController getOAuthClientController();

    /**
     * Returns a new instance of OAuth grant controller.
     *
     * @return OAuth grant controller
     */
    OAuthGrantController getOAuthGrantController();

    /**
     * Returns a new instance of OAuth token controller.
     *
     * @return OAuth token controller
     */
    OAuthTokenController getOAuthTokenController();

    /**
     * Disconnects the client from the server.
     */
    void close();

}
