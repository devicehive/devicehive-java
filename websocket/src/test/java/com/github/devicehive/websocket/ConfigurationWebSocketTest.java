/*
 *
 *
 *   ConfigurationWebSocketTest.java
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
import com.github.devicehive.rest.api.ConfigurationApi;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtRefreshToken;
import com.github.devicehive.rest.model.ValueProperty;
import com.github.devicehive.websocket.api.ConfigurationWS;
import com.github.devicehive.websocket.listener.ConfigurationListener;
import com.github.devicehive.websocket.model.repsonse.ConfigurationGetResponse;
import com.github.devicehive.websocket.model.repsonse.ConfigurationInsertResponse;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import retrofit2.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigurationWebSocketTest extends Helper {
    private static final String CONFIGURATION_NAME = "WS T3ZT ";
    private static final String CONFIGURATION_VALUE = "TEST VALUE";
    private static final RESTHelper restHelper = new RESTHelper();
    private CountDownLatch latch;
    private ConfigurationWS configurationWS;
    private String configurationName;
    private boolean configurationDeleted = false;
    private ApiClient apiClient = new ApiClient(getRestUrl());

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        configurationWS = client.createConfigurationWS();
        configurationName = CONFIGURATION_NAME + new Random().nextInt();
    }

    @After
    public void clean() throws InterruptedException {
        if (configurationDeleted) {
            return;
        }
        deleteConfiguration(configurationWS, configurationName);
    }

    @Test
    public void putConfigurationProperty() throws InterruptedException, IOException {
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
                Assert.assertTrue(false);
            }

        });
        configurationWS.put(null, configurationName, CONFIGURATION_VALUE);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getConfigurationProperty() throws InterruptedException, IOException {
        insertNewConfiguration();
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {
                Assert.assertEquals(configurationName, response.getConfiguration().getName());
                Assert.assertEquals(CONFIGURATION_VALUE, response.getConfiguration().getValue());
                latch.countDown();
            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        configurationWS.get(null, configurationName);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void deleteConfigurationProperty() throws InterruptedException, IOException {
        insertNewConfiguration();
        configurationWS.setListener(new ConfigurationListener() {
            @Override
            public void onGet(ConfigurationGetResponse response) {

            }

            @Override
            public void onPut(ConfigurationInsertResponse response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
                configurationDeleted = true;
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        configurationWS.delete(null, configurationName);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    private boolean insertNewConfiguration() throws IOException {
        AuthApi authApi = apiClient.createService(AuthApi.class);
        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(getRefreshToken());
        Response<JwtAccessToken> response = authApi.refreshTokenRequest(refreshToken).execute();

        if (response.isSuccessful()) {
            String accessToken = response.body().getAccessToken();
            apiClient.addAuthorization(ApiClient.AUTH_API_KEY, ApiKeyAuth.newInstance(accessToken));
            ValueProperty valueProperty = new ValueProperty();
            valueProperty.setValue(CONFIGURATION_VALUE);
            return apiClient.createService(ConfigurationApi.class).setProperty(configurationName, valueProperty).execute().isSuccessful();
        } else {
            throw new IOException("Can't get the token");
        }
    }
}
