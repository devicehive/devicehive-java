/*
 *
 *
 *   Helper.java
 *
 *   Copyright (C) 2017 DataArt
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.api.ConfigurationApi;
import com.github.devicehive.rest.api.DeviceApi;
import com.github.devicehive.rest.api.NetworkApi;
import com.github.devicehive.rest.api.UserApi;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.rest.model.JwtRequest;
import com.github.devicehive.rest.model.JwtToken;
import com.github.devicehive.rest.model.Network;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.rest.utils.Const;

import java.io.IOException;
import java.util.List;


import retrofit2.Response;


class Helper {

    private static final String LOGIN = "dhadmin";
    private static final String PASSWORD = "dhadmin_#911";
    private static final String URL = "http://playground.dev.devicehive.com";

    ApiClient client = new ApiClient(URL);

    boolean authenticate() throws IOException {
        AuthApi api = client.createService(AuthApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin(LOGIN);
        requestBody.setPassword(PASSWORD);
        Response<JwtToken> response = api.login(requestBody).execute();
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
}
