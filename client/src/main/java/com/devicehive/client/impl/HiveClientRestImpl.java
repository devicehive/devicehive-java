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

public class HiveClientRestImpl implements HiveClient {

    private final RestAgent restAgent;

    public HiveClientRestImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    @Override
    public ApiInfo getInfo() throws HiveException {
        return restAgent.getInfo();
    }

    @Override
    public void authenticate(String login, String password) throws HiveException {
        restAgent.authenticate(HivePrincipal.createUser(login, password));
    }

    @Override
    public void authenticate(String accessKey) throws HiveException {
        restAgent.authenticate(HivePrincipal.createAccessKey(accessKey));
    }

    @Override
    public AccessKeyAPI getAccessKeyAPI() {
        return new AccessKeyAPIImpl(restAgent);
    }

    @Override
    public CommandsAPI getCommandsAPI() {
        return new CommandsAPIRestImpl(restAgent);
    }

    @Override
    public DeviceAPI getDeviceAPI() {
        return new DeviceAPIImpl(restAgent);
    }

    @Override
    public NetworkAPI getNetworkAPI() {
        return new NetworkAPIImpl(restAgent);
    }

    @Override
    public NotificationsAPI getNotificationsAPI() {
        return new NotificationsAPIRestImpl(restAgent);
    }

    @Override
    public UserAPI getUserAPI() {
        return new UserAPIImpl(restAgent);
    }

    @Override
    public OAuthClientAPI getOAuthClientAPI() {
        return new OAuthClientAPIImpl(restAgent);
    }

    @Override
    public OAuthGrantAPI getOAuthGrantAPI() {
        return new OAuthGrantAPIImpl(restAgent);
    }

    @Override
    public OAuthTokenAPI getOAuthTokenAPI() {
        return new OAuthTokenAPIImpl(restAgent);
    }

    @Override
    public void close() {
        restAgent.close();
    }


}
