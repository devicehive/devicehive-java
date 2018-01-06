/*
 *
 *
 *   UserWebSocketTest.java
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
import com.github.devicehive.rest.api.UserApi;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtRefreshToken;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.StatusEnum;
import com.github.devicehive.rest.model.UserUpdate;
import com.github.devicehive.rest.model.UserWithNetwork;
import com.github.devicehive.websocket.api.UserWS;
import com.github.devicehive.websocket.listener.UserListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.UserGetCurrentResponse;
import com.github.devicehive.websocket.model.repsonse.UserGetNetworkResponse;
import com.github.devicehive.websocket.model.repsonse.UserGetResponse;
import com.github.devicehive.websocket.model.repsonse.UserInsertResponse;
import com.github.devicehive.websocket.model.repsonse.UserListResponse;
import com.google.gson.JsonObject;

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
public class UserWebSocketTest extends Helper {
    private static final String JSON_DATA = "{\"jsonString\": \"NEW STRING DATA\"}";
    private static final String LOGIN = "WS_L0G1N_DAT_1Z_UN1CK_";
    private static final String PASSWORD = "PASSWORD";
    private static final String NETWORK_NAME = "WS T3ZT NE7W0K ";
    private static final RoleEnum ROLE = RoleEnum.CLIENT;
    private static final StatusEnum STATUS = StatusEnum.ACTIVE;
    private static Long userId;
    private static Long networkId;
    private static final RESTHelper restHelper = new RESTHelper();
    private CountDownLatch latch;
    private UserWS userWS;
    private boolean required = true;
    private ApiClient apiClient = new ApiClient(getRestUrl());

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        required = true;
        latch = new CountDownLatch(1);
        userWS = client.createUserWS();
    }

    @After
    public void clean() throws IOException {
        if (required && userId != 0) {
            restHelper.deleteUsers(userId);
        }
    }

    @Test
    public void insertUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {
                userId = response.getUser().getId();
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getUser() throws InterruptedException, IOException {
        userId = insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
                UserWithNetwork user = response.getUser();
                Assert.assertEquals(userId, user.getId());
                latch.countDown();
            }

            @Override
            public void onInsert(UserInsertResponse response) {
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        userWS.get(null, userId);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void updateUser() throws InterruptedException, IOException {
        userId = insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        JsonObject updatedData = new JsonObject();
        updatedData.addProperty("customData",JSON_DATA);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setData(updatedData);

        userWS.update(null, userId, userUpdate);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void deleteUser() throws InterruptedException, IOException {
        required = false;
        userId = insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

                latch.countDown();
            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        userWS.delete(null, userId);
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getCurrentUser() throws InterruptedException, IOException {
        required = false;
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

                latch.countDown();
            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        userWS.getCurrent(null);
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void updateCurrentUser() throws InterruptedException, IOException {
        required = false;
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

                latch.countDown();
            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        JsonObject updatedData = new JsonObject();
        updatedData.addProperty("customData",JSON_DATA);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setData(updatedData);

        userWS.updateCurrent(null, userUpdate);
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }


    @Test
    public void listUsers() throws InterruptedException, IOException {
        userId = insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {
                Assert.assertNotEquals(0, response.getUsers().size());
                latch.countDown();
            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        userWS.list(null, null, LOGIN + "%", null, null, null, null, 30, 0);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void assignNetwork() throws InterruptedException, IOException {
        userId = insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

                latch.countDown();
            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error.toString());
            }

        });
        networkId = createTestNetwork();
        userWS.assignNetwork(null, userId, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);
        restHelper.deleteNetworks(networkId);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void unassignNetwork() throws InterruptedException, IOException {
        userId = insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {
                userWS.unassignNetwork(null, userId, networkId);
            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error.toString());
            }

        });


        networkId = createTestNetwork();
        userWS.assignNetwork(null, userId, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);
        restHelper.deleteNetworks(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getNetwork() throws InterruptedException, IOException {
        userId=insertNewUser();
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

                latch.countDown();
            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

                userWS.getNetwork(null, userId, networkId);
            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error.toString());
            }

        });
        networkId = createTestNetwork();
        userWS.assignNetwork(null, userId, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);
        restHelper.deleteNetworks(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    private UserUpdate createNewUserUpdate() {
        return createNewUserUpdate(LOGIN);
    }

    private UserUpdate createNewUserUpdate(String userLogin) {
        UserUpdate userUpdate = new UserUpdate();
        String login = userLogin + new Random().nextLong();
        System.out.println("User login for test: " + login);
        userUpdate.setLogin(login);
        userUpdate.setPassword(PASSWORD);
        userUpdate.setRole(ROLE);
        userUpdate.setStatus(STATUS);
        return userUpdate;
    }

    private Long createTestNetwork() throws IOException {
        String networkName = NETWORK_NAME + new Random().nextLong();
        System.out.println("Network name for test: " + networkName);
        NetworkId networkId = restHelper.createNetwork(networkName);
        return networkId.getId();
    }


    private long insertNewUser() throws IOException {
        AuthApi authApi = apiClient.createService(AuthApi.class);
        JwtRefreshToken refreshToken = new JwtRefreshToken();
        refreshToken.setRefreshToken(getRefreshToken());
        Response<JwtAccessToken> response = authApi.refreshTokenRequest(refreshToken).execute();

        if (response.isSuccessful()) {
            String accessToken = response.body().getAccessToken();
            UserUpdate userUpdate = createNewUserUpdate();
            apiClient.addAuthorization(ApiClient.AUTH_API_KEY, ApiKeyAuth.newInstance(accessToken));
            return apiClient.createService(UserApi.class).insertUser(userUpdate).execute().body().getId();
        } else {
            throw new IOException("Can't get the token");
        }
    }


}
