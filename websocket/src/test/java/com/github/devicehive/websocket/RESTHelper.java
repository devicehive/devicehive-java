package com.github.devicehive.websocket;

import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.api.ConfigurationApi;
import com.github.devicehive.rest.api.DeviceApi;
import com.github.devicehive.rest.api.NetworkApi;
import com.github.devicehive.rest.api.UserApi;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.model.*;
import com.github.devicehive.rest.utils.Const;

import java.io.IOException;
import java.util.List;


import retrofit2.Response;


class RESTHelper {

    private static final String LOGIN = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    private static final String URL = "***REMOVED***";

    ApiClient client = new ApiClient(URL);

    RESTHelper() {
        authenticate();
    }

    boolean authenticate() {
        AuthApi api = client.createService(AuthApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin(LOGIN);
        requestBody.setPassword(PASSWORD);
        Response<JwtToken> response = null;
        try {
            response = api.login(requestBody).execute();
        } catch (IOException e) {
            System.out.println("Login attempt failed.");
            return false;
        }
        if (response.isSuccessful()) {
            client.addAuthorization(ApiClient.AUTH_API_KEY, ApiKeyAuth.newInstance(response.body().getAccessToken()));
        }
        return response.isSuccessful();
    }

    boolean createDevice(String deviceId) throws IOException {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        DeviceApi deviceApi = client.createService(DeviceApi.class);
        NetworkApi networkApi = client.createService(NetworkApi.class);
        Response<List<Network>> networkResponse = networkApi.list(null, null, null,
                null, null, null).execute();
        List<Network> networks = networkResponse.body();

        if (networks != null && !networks.isEmpty()) {
            device.setNetworkId(networks.get(0).getId());
            Response<Void> response = deviceApi.register(device, deviceId).execute();
            return response.isSuccessful();
        } else {
            return false;
        }
    }

    NetworkId createNetwork(String networkName) throws IOException {
        NetworkApi networkApi = client.createService(NetworkApi.class);
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(networkName);

        Response<NetworkId> insertResponse = networkApi.insert(networkUpdate).execute();
        return insertResponse.body();
    }

    boolean deleteDevices(String... ids) throws IOException {
        int count = 0;
        DeviceApi api = client.createService(DeviceApi.class);
        for (String id : ids) {
            if (api.delete(id).execute().isSuccessful()) {
                count++;
            } else {
                return false;
            }
        }
        return count == ids.length;
    }

    boolean deleteNetworks(Long... ids) throws IOException {
        int count = 0;
        NetworkApi networkApi = client.createService(NetworkApi.class);
        for (Long id : ids) {
            if (networkApi.delete(id).execute().isSuccessful()) {
                count++;
            } else {
                return false;
            }
        }
        return count == ids.length;
    }

    boolean deleteUsers(Long... ids) throws IOException {
        int count = 0;
        UserApi userApi = client.createService(UserApi.class);
        for (Long id : ids) {
            if (userApi.deleteUser(id).execute().isSuccessful()) {
                count++;
            } else {
                return false;
            }
        }
        return count == ids.length;
    }

    boolean deleteConfigurations(String... names) throws IOException {
        int count = 0;
        ConfigurationApi configurationApi = client.createService(ConfigurationApi.class);
        for (String name : names) {
            if (configurationApi.deleteProperty(name).execute().isSuccessful()) {
                count++;
            } else {
                return false;
            }
        }
        return count == names.length;
    }

    void cleanUpNetworks(String networkNamePrefix) throws IOException {
        NetworkApi networkApi = client.createService(NetworkApi.class);

        Response<List<Network>> getResponse = networkApi.list(null, networkNamePrefix + "%",
                null, null, Integer.MAX_VALUE, 0).execute();
        List<Network> existingNetworks = getResponse.body();
        for (Network network: existingNetworks) {
            deleteNetworks(network.getId());
        }
    }


    void cleanUpUsers(String userLoginPrefix) throws IOException {
        UserApi userApi = client.createService(UserApi.class);

        Response<List<UserVO>> getResponse = userApi.list(null, userLoginPrefix + "%",
                null, null, null, null, Integer.MAX_VALUE, 0).execute();
        List<UserVO> existingUsers = getResponse.body();
        for (UserVO user: existingUsers) {
            deleteUsers(user.getId());
        }
    }

    void cleanUpConfigurations(String configurationName) throws IOException {
        deleteConfigurations(configurationName);
    }

    void cleanUpDevices(String deviceNamePrefix) throws IOException {
        DeviceApi deviceApi = client.createService(DeviceApi.class);

        Response<List<Device>> getResponse = deviceApi.list(null, deviceNamePrefix + "%",
                null, null, null, null, Integer.MAX_VALUE, 0).execute();
        List<Device> existingDevices = getResponse.body();
        for (Device device: existingDevices) {
            deleteDevices(device.getId());
        }
    }
}
