package com.devicehive.client.websocket.api.impl;


import com.devicehive.client.api.*;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.HiveMessage;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.context.HivePrincipal;
import com.devicehive.client.websocket.context.RestAgent;
import org.joda.time.DateTime;

/**
 * Implementation of {@link HiveClient} that uses REST transport.
 */
public class HiveClientRestImpl implements HiveClient, HiveMessage {

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
    public AccessKeyApi getAccessKeyAPI() {
        return restAgent.createService(AccessKeyApi.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommandApi getCommandsAPI() {
        return restAgent.createService(DeviceCommandApi.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceApi getDeviceAPI() {
        return restAgent.createService(DeviceApi.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkApi getNetworkAPI() {
        return restAgent.createService(NetworkApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceNotificationApi getNotificationsAPI() {
        return restAgent.createService(DeviceNotificationApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UserApi getUserAPI() {
        return restAgent.createService(UserApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthClientApi getOAuthClientAPI() {
        return restAgent.createService(OAuthClientApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthGrantApi getOAuthGrantAPI() {
        return restAgent.createService(OAuthGrantApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        restAgent.close();
    }


    @Override
    public DateTime getTimestamp() throws HiveException {
        return restAgent.getServerTimestamp();
    }
}
