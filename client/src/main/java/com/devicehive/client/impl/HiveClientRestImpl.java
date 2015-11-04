package com.devicehive.client.impl;


import com.devicehive.client.*;
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
    public AccessKeyController getAccessKeyController() {
        return new AccessKeyControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandsController getCommandsController() {
        return new CommandsControllerRestImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceController getDeviceController() {
        return new DeviceControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkController getNetworkController() {
        return new NetworkControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationsController getNotificationsController() {
        return new NotificationsControllerRestImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserController getUserController() {
        return new UserControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthClientController getOAuthClientController() {
        return new OAuthClientControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrantController getOAuthGrantController() {
        return new OAuthGrantControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthTokenController getOAuthTokenController() {
        return new OAuthTokenControllerImpl(restAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        restAgent.close();
    }


}
