/*
 *
 *
 *   Helper.java
 *
 *   Copyright (C) 2018 DataArt
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

package com.github.devicehive.websocket;

import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.rest.model.JwtRequest;
import com.github.devicehive.rest.model.JwtToken;
import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.websocket.api.AuthListener;
import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.api.NetworkWS;
import com.github.devicehive.websocket.api.TokenWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.github.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.github.devicehive.websocket.model.repsonse.TokenRefreshResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class Helper {
    private String restUrl;
    private String accessToken;
    private String refreshToken;
    private String login;
    private String password;
    WebSocketClient client;

    private TokenWS tokenWS;
    private DeviceWS deviceWS;

    Helper() {
        restUrl = System.getProperty("url");
        login = System.getProperty("login");
        password = System.getProperty("password");
        client = new WebSocketClient.Builder().url(System.getProperty("wsUrl")).build();
        tokenWS = client.createTokenWS();
        deviceWS = client.createDeviceWS();
        try {
            JwtToken jwtToken = login(restUrl);
            accessToken = jwtToken.getAccessToken();
            refreshToken = jwtToken.getRefreshToken();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public JwtToken login(String url) throws IOException {
        String login = System.getProperty("login");
        String password = System.getProperty("password");
        ApiClient apiClient = new ApiClient(url);

        AuthApi authApi = apiClient.createService(AuthApi.class);
        JwtRequest body = new JwtRequest();
        body.setLogin(login);
        body.setPassword(password);

        return authApi.login(body).execute().body();
    }

    public String getRestUrl() {
        return restUrl;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    int awaitTimeout = 10;
    TimeUnit awaitTimeUnit = TimeUnit.SECONDS;


    void authenticate() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        client.setListener(new AuthListener() {
            @Override
            public void onSuccess(ResponseAction responseAction) {
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {
                refresh(latch);
            }
        });

        if (accessToken != null && !accessToken.isEmpty()) {
            client.authenticate(accessToken);
        } else {
            refresh(latch);
        }
        latch.await(awaitTimeout, awaitTimeUnit);
    }

    private void refresh(final CountDownLatch latch) {
        tokenWS.setListener(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {

            }

            @Override
            public void onCreate(TokenGetResponse response) {

            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {
                latch.countDown();
                accessToken = response.getAccessToken();
                client.authenticate(accessToken);
            }

            @Override
            public void onError(ErrorResponse error) {
                latch.countDown();
            }
        });
        tokenWS.refresh(null, refreshToken);
    }

    void deleteConfiguration(ConfigurationWS configurationWS, String name)
            throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {

            }

            @Override
            public void onDelete(ResponseAction response) {
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });

        configurationWS.delete(null, name);
        latch.await(awaitTimeout, awaitTimeUnit);
    }

    void registerDevice(DeviceWS deviceWS, String deviceId, String deviceName, Long networkId)
            throws InterruptedException {
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(deviceName);
        deviceUpdate.setNetworkId(networkId);

        deviceWS.save(null, deviceId, deviceUpdate);
    }

    void registerDevice(String deviceId) throws InterruptedException {
        authenticate();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(deviceId);
        deviceWS.save(null, deviceId, deviceUpdate);
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {

            }

            @Override
            public void onGet(Device response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {
                atomicBoolean.set(true);
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        latch.await(awaitTimeout, TimeUnit.SECONDS);
        deviceWS.setListener(null);
    }

    void deleteDevice(String id) {
        deviceWS.delete(null, id);
    }

    public NetworkUpdate getNetworkUpdate(String networkName) {
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(networkName);
        return networkUpdate;
    }

    void deleteNetwork(NetworkWS networkWS, long id) {
        networkWS.delete(null, id);
    }
}