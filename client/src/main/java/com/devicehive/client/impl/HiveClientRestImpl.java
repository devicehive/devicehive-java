package com.devicehive.client.impl;


import com.devicehive.client.AccessKeyAPI;
import com.devicehive.client.CommandsAPI;
import com.devicehive.client.DeviceAPI;
import com.devicehive.client.HiveClient;
import com.devicehive.client.NetworkAPI;
import com.devicehive.client.NotificationsAPI;
import com.devicehive.client.OAuthClientAPI;
import com.devicehive.client.OAuthGrantAPI;
import com.devicehive.client.OAuthTokenAPI;
import com.devicehive.client.UserAPI;
import com.devicehive.client.impl.context.HivePrincipal;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.exceptions.HiveException;

/**
 * Implementation of {@link HiveClient} that uses REST transport.
 */
public class HiveClientRestImpl implements HiveClient {

    private final RestAgent restAgent;

    /**
     * Initializes a client with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    public HiveClientRestImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiInfo getInfo() throws HiveException {
        return restAgent.getInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String login, String password) throws HiveException {
        restAgent.authenticate(HivePrincipal.createUser(login, password));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String accessKey) throws HiveException {
        restAgent.authenticate(HivePrincipal.createAccessKey(accessKey));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessKeyAPI getAccessKeyAPI() {
        return new AccessKeyAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandsAPI getCommandsAPI() {
        return new CommandsAPIRestImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceAPI getDeviceAPI() {
        return new DeviceAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkAPI getNetworkAPI() {
        return new NetworkAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationsAPI getNotificationsAPI() {
        return new NotificationsAPIRestImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAPI getUserAPI() {
        return new UserAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthClientAPI getOAuthClientAPI() {
        return new OAuthClientAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrantAPI getOAuthGrantAPI() {
        return new OAuthGrantAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthTokenAPI getOAuthTokenAPI() {
        return new OAuthTokenAPIImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        restAgent.close();
    }


}
