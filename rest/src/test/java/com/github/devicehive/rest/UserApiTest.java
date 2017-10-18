/*
 *
 *
 *   UserApiTest.java
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

import com.github.devicehive.rest.api.UserApi;
import com.github.devicehive.rest.model.*;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class UserApiTest extends Helper {
    private static final String JSON_DATA = "{\"jsonString\": \"NEW STRING DATA\"}";
    private static final String LOGIN = "L0G1N_DAT_1Z_UN1CK";
    private static final String PASSWORD = "PASSWORD";
    private static final String NETWORK_NAME = "TEST NETWORK";
    private static final User.RoleEnum ROLE = User.RoleEnum.ADMIN;
    private static final User.StatusEnum STATUS = User.StatusEnum.ACTIVE;

    private UserUpdate createNewAdminUserUpdate() {
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setLogin(LOGIN);
        userUpdate.setPassword(PASSWORD);
        userUpdate.setRole(ROLE);
        userUpdate.setStatus(STATUS);
        return userUpdate;
    }

    private UserUpdate createNewAdminUserUpdate(String userLogin) {
        UserUpdate userUpdate = createNewAdminUserUpdate();
        userUpdate.setLogin(userLogin);
        return userUpdate;
    }

    @Test
    public void getCurrentUser() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);

        Response<UserWithNetwork> getResponse = userApi.getCurrent().execute();
        Assert.assertTrue(getResponse.isSuccessful());
    }

    @Test
    public void updateCurrentUser() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);

        JsonStringWrapper updatedData = new JsonStringWrapper();
        updatedData.setJsonString(JSON_DATA);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setData(updatedData);

        Response<Void> putResponse = userApi.updateCurrentUser(userUpdate).execute();
        Assert.assertTrue(putResponse.isSuccessful());
    }

    @Test
    public void insertUser() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);
        deleteUsers(userId);
    }

    @Test
    public void deleteUser() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        Response<Void> deleteResponse = userApi.deleteUser(userId).execute();
        Assert.assertTrue(deleteResponse.isSuccessful());
    }

    @Test
    public void getUser() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
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
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());

        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);

        JsonStringWrapper updatedData = new JsonStringWrapper();
        updatedData.setJsonString(JSON_DATA);
        UserUpdate userUpdateData = new UserUpdate();
        userUpdateData.setData(updatedData);

        Response<Void> putResponse = userApi.updateUser(userId, userUpdateData).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        deleteUsers(userId);
    }

    @Test
    public void assignNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());
        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);
        NetworkId testNetworkId = createNetwork(NETWORK_NAME);

        Response<Void> putResponse = userApi.assignNetwork(userId, testNetworkId.getId()).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        deleteUsers(userId);
        deleteNetworks(testNetworkId.getId());
    }

    @Test
    public void getNetwork() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());
        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);
        NetworkId testNetworkId = createNetwork(NETWORK_NAME);

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
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);
        UserUpdate userUpdate = createNewAdminUserUpdate();

        Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
        Assert.assertTrue(postResponse.isSuccessful());
        Long userId = postResponse.body().getId();
        Assert.assertNotNull(userId);
        NetworkId testNetworkId = createNetwork(NETWORK_NAME);

        Response<Void> putResponse = userApi.unassignNetwork(userId, testNetworkId.getId()).execute();
        Assert.assertTrue(putResponse.isSuccessful());

        deleteUsers(userId);
        deleteNetworks(testNetworkId.getId());
    }

    @Test
    public void listUsers() throws IOException {
        boolean authenticated = authenticate();
        Assert.assertTrue(authenticated);

        UserApi userApi = client.createService(UserApi.class);

        int userAmount = 5;
        Long[] userIds = new Long[userAmount];
        for (int j = 0; j < userAmount; ++j) {
            String userLogin = String.format("%s_%d", LOGIN, j);
            UserUpdate userUpdate = createNewAdminUserUpdate(userLogin);

            Response<UserVO> postResponse = userApi.insertUser(userUpdate).execute();
            Assert.assertTrue(postResponse.isSuccessful());

            Long userId = postResponse.body().getId();
            Assert.assertNotNull(userId);
            userIds[j] = userId;
        }

        Response<List<UserVO>> listResponse = userApi.list(null, LOGIN + "%",
                null, null, null, null, 2 * userAmount, 0).execute();
        Assert.assertTrue(listResponse.isSuccessful());
        Assert.assertEquals(userAmount, listResponse.body().size());

        Assert.assertTrue(deleteUsers(userIds));
    }

}
