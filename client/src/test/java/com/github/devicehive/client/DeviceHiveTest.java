/*
 *
 *
 *   DeviceHiveTest.java
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

package com.github.devicehive.client;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceFilter;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.client.model.DeviceNotificationsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.NetworkFilter;
import com.github.devicehive.client.model.NotificationFilter;
import com.github.devicehive.client.model.UserFilter;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.client.service.User;
import com.github.devicehive.rest.model.ApiInfo;
import com.github.devicehive.rest.model.Configuration;
import com.github.devicehive.rest.model.JwtAccessToken;
import com.github.devicehive.rest.model.JwtToken;
import com.github.devicehive.rest.model.NetworkVO;
import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.StatusEnum;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceHiveTest extends Helper {

    private static final String DEVICE_ID = "271990123";
    private static final String DEVICE_ID2 = "271990";

    private DeviceHive deviceHive;

    @Before
    public void init() throws IOException {
        String url = System.getProperty("url");
        JwtToken token = login(url);

        deviceHive = DeviceHive.getInstance().init(
                url,
                token.getRefreshToken(),
                token.getAccessToken());
    }

    @Test
    public void apiInfoTest() {
        DHResponse<ApiInfo> response = deviceHive.getInfo();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void createToken() throws IOException {
        deviceHive.login(System.getProperty("login"), System.getProperty("password"));

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void createTokenViaToken() throws IOException {

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void refreshToken() throws IOException {
        deviceHive.login("***REMOVED**", "***REMOVED***");

        DHResponse<JwtAccessToken> response2 = deviceHive.refreshToken();
        Assert.assertTrue(response2.isSuccessful());
    }

    @Test
    public void getConfigurationProperty() throws IOException {
        DHResponse<Configuration> response = deviceHive.getProperty("jwt.secret");
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void setConfigurationProperty() throws IOException {

        DHResponse<Configuration> response = deviceHive.setProperty("jwt.secret2", "device2");


        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void deleteConfigurationProperty() throws IOException {

        DHResponse<Configuration> response1 = deviceHive.setProperty("jwt.secret2", "device2");
        Assert.assertTrue(response1.isSuccessful());

        DHResponse<Void> response2 = deviceHive.removeProperty("jwt.secret2");
        Assert.assertTrue(response2.isSuccessful());
    }

    @Test
    public void createNetworkAndDelete() throws IOException {
        DHResponse<com.github.devicehive.client.service.Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        Assert.assertTrue(response.isSuccessful());
        if (response.isSuccessful()) {
            DHResponse<Void> response2 = deviceHive.removeNetwork(response.getData().getId());
            Assert.assertTrue(response2.isSuccessful());
        }
    }

    @Test
    public void getNetwork() throws IOException {
        DHResponse<com.github.devicehive.client.service.Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        Assert.assertTrue(response.isSuccessful());
        DHResponse<NetworkVO> response2 = deviceHive.getNetwork(response.getData().getId());
        Assert.assertTrue(response2.isSuccessful());

        DHResponse<Void> response3 = deviceHive.removeNetwork(response.getData().getId());
        Assert.assertTrue(response3.isSuccessful());
    }

    @Test
    public void updateNetwork() throws IOException {
        DHResponse<com.github.devicehive.client.service.Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        Assert.assertTrue(response.isSuccessful());


        DHResponse<NetworkVO> response2 = deviceHive.getNetwork(response.getData().getId());
        Assert.assertTrue(response2.isSuccessful());

        response.getData().setName("Java Client Lib Renamed");
        response.getData().save();
        DHResponse<NetworkVO> response3 = deviceHive.getNetwork(response.getData().getId());
        Assert.assertTrue(response3.isSuccessful());
        Assert.assertEquals("Java Client Lib Renamed", response3.getData().getName());

        DHResponse<Void> response4 = deviceHive.removeNetwork(response.getData().getId());
        Assert.assertTrue(response4.isSuccessful());
    }

    @Test
    public void listNetwork() throws IOException {
        NetworkFilter filter = new NetworkFilter();
        filter.setNamePattern("%network%");
        DHResponse<List<com.github.devicehive.client.service.Network>> response = deviceHive.listNetworks(filter);
        Assert.assertTrue(response.isSuccessful());

    }


    @Test
    public void subscribeManyNotifications() throws IOException, InterruptedException {
        //Fixme add Asserts
        final CountDownLatch latch = new CountDownLatch(1);

        final NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames("notificationA", "notificationB");
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));
        final List<String> ids = new ArrayList<String>();
        ids.add(DEVICE_ID);
        ids.add(DEVICE_ID2);
        deviceHive.subscribeNotifications(ids, notificationFilter, new DeviceNotificationsCallback() {
            public void onSuccess(List<DeviceNotification> notifications) {
                latch.countDown();
                Assert.assertTrue(true);
            }

            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                deviceHive.getDevice(DEVICE_ID).getData().sendNotification("notificationA", null);
            }
        }), 5, TimeUnit.SECONDS);
        latch.await(30, TimeUnit.SECONDS);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDevices() {
        DHResponse<List<Device>> devices = deviceHive.listDevices(new DeviceFilter());
        Assert.assertTrue(devices.isSuccessful());
    }


    @Test
    public void getUsers() {
        UserFilter filter = new UserFilter();
        DHResponse<List<com.github.devicehive.client.service.User>> users = deviceHive.getUsers(filter);
        Assert.assertTrue(users.isSuccessful());
    }

    @Test
    public void createAndDeleteUser() {
        DHResponse<User> user = deviceHive.createUser("javaLibTest", "123456",
                RoleEnum.ADMIN, StatusEnum.ACTIVE, null);
        Assert.assertTrue(user.isSuccessful());
        deleteUser(user.getData().getId());
    }

    private void deleteUser(long userId) {
        DHResponse<Void> delete = deviceHive.removeUser(userId);
        Assert.assertTrue(delete.isSuccessful());
    }

    @Test
    public void getCurrentUser() {
        DHResponse<com.github.devicehive.client.service.User> userDHResponse = deviceHive.getCurrentUser();
        Assert.assertTrue(userDHResponse.isSuccessful());
    }
}
