package com.devicehive.client.websocket.api.impl;

import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.api.HiveClient;
import com.devicehive.client.api.NetworkApi;
import com.devicehive.client.api.UserApi;
import com.devicehive.client.model.ApiInfoVO;
import com.devicehive.client.model.HiveMessage;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.context.HivePrincipal;
import com.devicehive.client.websocket.context.RestClientWIP;
import org.joda.time.DateTime;

/**
 * Implementation of {@link HiveClient} that uses REST transport.
 */
public class HiveClientRestImpl implements HiveClient, HiveMessage {

    private final RestClientWIP restClient;

    /**
     * Initializes a client with {@link RestClientWIP} to use for requests.
     *
     * @param restClient a RestClientWIP to use for requests
     */
    public HiveClientRestImpl(RestClientWIP restClient) {
        this.restClient = restClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiInfoVO getInfo() throws HiveException {
        return restClient.getInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String login, String password) throws HiveException {
        restClient.authenticate(HivePrincipal.createUser(login, password));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String accessKey) throws HiveException {
        restClient.authenticate(HivePrincipal.createAccessKey(accessKey));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommandApi getCommandsAPI() {
        return restClient.createService(DeviceCommandApi.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceApi getDeviceAPI() {
        return restClient.createService(DeviceApi.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkApi getNetworkAPI() {
        return restClient.createService(NetworkApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceNotificationApi getNotificationsAPI() {
        return restClient.createService(DeviceNotificationApi.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UserApi getUserAPI() {
        return restClient.createService(UserApi.class);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        restClient.close();
    }


    @Override
    public DateTime getTimestamp() throws HiveException {
        return restClient.getServerTimestamp();
    }
}
