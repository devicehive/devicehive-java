/*
 *
 *
 *   UserApiTest.java
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.UserApi;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.StatusEnum;
import com.github.devicehive.rest.model.UserInsert;
import com.github.devicehive.rest.model.UserNetworkResponse;
import com.github.devicehive.rest.model.UserResponse;
import com.github.devicehive.rest.model.UserUpdate;
import com.github.devicehive.rest.model.UserVO;
import com.github.devicehive.rest.model.UserWithNetwork;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import retrofit2.Response;

public class UserApiTest extends Helper {
    private static final String JSON_DATA = "{\"jsonString\": \"NEW STRING DATA\"}";
    private static final String LOGIN = "HTTP_L0G1N_DAT_1Z_UN1CK_";
    private static final String PASSWORD = "PASSWORD";
    private static final String NETWORK_NAME = "HTTP T3ZT NE7W0K ";
    private static final RoleEnum ROLE = RoleEnum.CLIENT;
    private static final StatusEnum STATUS = StatusEnum.ACTIVE;

    private UserUpdate createNewUserUpdate() {
        UserUpdate userUpdate = createNewUserUpdate(LOGIN);
        return userUpdate;
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

    private NetworkId createTestNetwork() throws IOException {
        String networkName = NETWORK_NAME + new Random().nextLong();
        System.out.println("Network name for test: " + networkName);
        NetworkId networkId = createNetwork(networkName);
        return networkId;
    }

    private void authorise() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);
    }

    @Before
    public void preTest() throws IOException {
        authorise();
    }

    @Test
    public void getCurrentUser() throws IOException {
        UserApi userApi = client.createService(UserApi.class);

        Response<UserWithNetwork> getResponse = userApi.getCurrent().execute();
        Assert.assertTrue(getResponse.isSuccessful());
    }

    @Test
    public void updateCurrentUser() throws IOException {
        UserApi userApi = client.createService(UserApi.class);

        JsonObject params = new JsonObject();
        params.addProperty("customData",JSON_DATA);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setData(params);

        Response<Void> putResponse = userApi.updateCurrentUser(userUpdate).execute();
        Assert.assertTrue(putResponse.isSuccessful());
    }

    @Test
    public void insertUser() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);
        deleteUsers(userId);
    }

    @Test
    public void deleteUser() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        Response<Void> deleteResponse = userApi.deleteUser(userId).execute();
        Assert.assertTrue(deleteResponse.isSuccessful());
    }

    @Test
    public void getUser() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        Response<UserResponse> getResponse = userApi.getUser(userId).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertNotNull(getResponse.body().getId());
        Assert.assertEquals(userId, getResponse.body().getId());

        deleteUsers(userId);
    }

    @Test
    public void updateUser() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        JsonObject updatedData = new JsonObject();
        updatedData.addProperty("customData",JSON_DATA);
        UserUpdate userUpdateData = new UserUpdate();
        userUpdateData.setData(updatedData);

        Response<Void> putResponse = userApi.updateUser(userId, userUpdateData).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        deleteUsers(userId);
    }

    @Test
    public void assignNetwork() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());
        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        NetworkId testNetworkId = createTestNetwork();

        Response<Void> putResponse = userApi.assignNetwork(userId, testNetworkId.getId()).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        deleteUsers(userId);
        deleteNetworks(testNetworkId.getId());
    }

    @Test
    public void getNetwork() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());
        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        NetworkId testNetworkId = createTestNetwork();

        Response<Void> putResponse = userApi.assignNetwork(userId, testNetworkId.getId()).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        Response<UserNetworkResponse> getResponse = userApi.getNetwork(userId, testNetworkId.getId()).execute();
        Assert.assertTrue(getResponse.isSuccessful());
        Assert.assertNotNull(getResponse.body().getNetwork());
        Assert.assertNotNull(getResponse.body().getNetwork().getId());
        Assert.assertEquals(testNetworkId.getId(), getResponse.body().getNetwork().getId());

        deleteUsers(userId);
        deleteNetworks(testNetworkId.getId());
    }

    @Test
    public void unassignNetwork() throws IOException {
        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewUserUpdate();

        Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());
        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        NetworkId testNetworkId = createTestNetwork();

        Response<Void> putResponse = userApi.unassignNetwork(userId, testNetworkId.getId()).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        deleteUsers(userId);
        deleteNetworks(testNetworkId.getId());
    }

    @Test
    public void listUsers() throws IOException {
        UserApi userApi = client.createService(UserApi.class);

        int userAmount = 5;
        Long[] userIds = new Long[userAmount];
        for (int j = 0; j < userAmount; ++j) {
            String userLogin = String.format("%s%d_", LOGIN, j);
            UserUpdate userUpdate = createNewUserUpdate(userLogin);

            Response<UserInsert> postResponse = userApi.insertUser(userUpdate).execute();
            Assert.assertTrue(postResponse.isSuccessful());

            Long userId = postResponse.body().getId();
            Assert.assertNotNull(userId);
            userIds[j] = userId;
        }

        Response<List<UserVO>> listResponse = userApi.list(null, LOGIN + "%",
                null, null, null, null, 2 * userAmount, 0).execute();
        Assert.assertTrue(listResponse.isSuccessful());
        Assert.assertNotEquals(0, listResponse.body().size());

        Assert.assertTrue(deleteUsers(userIds));
    }

}
