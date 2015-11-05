package com.devicehive.client;


import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.exceptions.HiveException;

/**
 * Hive client that represents the number of API getters methods. APIs are used only to delegate methods
 * with similar logic to some container. The separation of client APIs is equal or similar to the server's
 * API.
 */
public interface HiveClient extends AutoCloseable {

    /**
     * Requests API information
     *
     * @return API info
     */
    ApiInfo getInfo() throws HiveException;

    /**
     * Authenticates client as user (by login and password). Permissions will be determined by user's role.
     *
     * @param login    login
     * @param password password
     */
    void authenticate(String login, String password) throws HiveException;

    /**
     * Authenticates client by access key. Permissions will be determined by the access key permissions.
     *
     * @param key access key
     */
    void authenticate(String key) throws HiveException;

    /**
     * Return new instance of access key API
     *
     * @return access key API
     */
    AccessKeyAPI getAccessKeyAPI();

    /**
     * Return new instance of command API
     *
     * @return command API
     */
    CommandsAPI getCommandsAPI();

    /**
     * Return new instance of device API
     *
     * @return device API
     */
    DeviceAPI getDeviceAPI();

    /**
     * Return new instance of network API
     *
     * @return network API
     */
    NetworkAPI getNetworkAPI();

    /**
     * Return new instance of notification API
     *
     * @return notification API
     */
    NotificationsAPI getNotificationsAPI();

    /**
     * Return new instance of user API.
     *
     * @return user API
     */
    UserAPI getUserAPI();

    /**
     * Return new instance of OAuth API.
     *
     * @return user API
     */
    OAuthClientAPI getOAuthClientAPI();

    /**
     * Return new instance of OAuth client API.
     *
     * @return user API
     */
    OAuthGrantAPI getOAuthGrantAPI();

    /**
     * Return new instance of OAuth token API.
     *
     * @return user API
     */
    OAuthTokenAPI getOAuthTokenAPI();


    void close();

}
