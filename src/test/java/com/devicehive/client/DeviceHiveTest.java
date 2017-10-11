package com.devicehive.client;

import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.*;
import com.devicehive.client.service.Device;
import com.devicehive.client.service.DeviceHive;
import com.devicehive.client.service.Network;
import com.devicehive.client.service.User;
import com.devicehive.rest.model.*;
import com.devicehive.websocket.model.StatusEnum;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceHiveTest {

    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.DVRKVgrtnv35MWwxR1T8bLm83-RJCfloYuoEjvYPQ4s";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJSRUZSRVNIIn19.7alYTD5kb_imglE7NyRhjQBFqXhqpfJJs-ZA68yJZiQ";

    private static final String DEVICE_ID = "271990123";
    private static final String DEVICE_ID2 = "271990";

    private DeviceHive deviceHive = DeviceHive.getInstance()
            .init(URL, new TokenAuth(refreshToken, accessToken));

    @Test
    public void apiInfoTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        deviceHive.getInfo(new ResponseCallback<ApiInfo>() {
            public void onResponse(DHResponse<ApiInfo> response) {
                Assert.assertTrue(response.isSuccessful());
                latch.countDown();
            }
        });
        latch.await(20, TimeUnit.SECONDS);
        Assert.assertTrue(deviceHive.getInfo().isSuccessful());

    }

    @Test
    public void createToken() throws IOException {
        deviceHive.login("dhadmin", "dhadmin_#911");

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
        deviceHive.login("dhadmin", "dhadmin_#911");

        DHResponse<JwtAccessToken> response2 = deviceHive.refreshToken();
        Assert.assertTrue(response2.isSuccessful());
    }

    @Test
    public void getConfigurationProperty() throws IOException {
        DHResponse<Configuration> response = deviceHive.getProperty("jwt.secret");
        System.out.println(response);
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
        DHResponse<Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        Assert.assertTrue(response.isSuccessful());
        if (response.isSuccessful()) {
            DHResponse<Void> response2 = deviceHive.removeNetwork(response.getData().getId());
            Assert.assertTrue(response2.isSuccessful());
        }
    }

    @Test
    public void getNetwork() throws IOException {
        DHResponse<Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        Assert.assertTrue(response.isSuccessful());
        DHResponse<NetworkVO> response2 = deviceHive.getNetwork(response.getData().getId());
        Assert.assertTrue(response2.isSuccessful());

        DHResponse<Void> response3 = deviceHive.removeNetwork(response.getData().getId());
        Assert.assertTrue(response3.isSuccessful());
    }

    @Test
    public void updateNetwork() throws IOException {
        DHResponse<Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
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
        DHResponse<List<Network>> response = deviceHive.listNetworks(filter);
        Assert.assertTrue(response.isSuccessful());

    }


    @Test
    public void subscribeManyNotifications() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames("notificationA", "notificationB");
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));
        final List<String> ids = new ArrayList<String>();
        ids.add(DEVICE_ID);
        ids.add(DEVICE_ID2);
        deviceHive.subscribeNotifications(ids, notificationFilter, new DeviceNotificationsCallback() {
            public void onSuccess(List<com.devicehive.client.model.DeviceNotification> notifications) {
            }

            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                notificationFilter.setNotificationNames("notificationZ");
                deviceHive.unsubscribeNotifications(ids, notificationFilter);
            }
        }), 30, TimeUnit.SECONDS);
        latch.await(60, TimeUnit.SECONDS);
    }

    @Test
    public void getDevices() {
        DHResponse<List<Device>> devices = deviceHive.listDevices(new DeviceFilter());
    }


    @Test
    public void getUsers() {
        UserFilter filter = new UserFilter();
        DHResponse<List<User>> users = deviceHive.getUsers(filter);
        Assert.assertTrue(users.isSuccessful());
        Method[] allMethods = Device.class.getDeclaredMethods();
        for (Method method : allMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                System.out.println(method.getName());
                // use the method
            }
        }
    }

    @Test
    public void createAndDeleteUser() {
        DHResponse<User> user = deviceHive.createUser("javaLibTest", "123456",
                com.devicehive.rest.model.User.RoleEnum.NUMBER_0, StatusEnum.ACTIVE, null);
        Assert.assertTrue(user.isSuccessful());
        deleteUser(user.getData().getId());
    }

    private void deleteUser(long userId) {
        DHResponse<Void> delete = deviceHive.removeUser(userId);
        Assert.assertTrue(delete.isSuccessful());
    }
    @Test
    public void getCurrentUser(){
        DHResponse<User> userDHResponse=deviceHive.getCurrentUser();
        Assert.assertTrue(userDHResponse.isSuccessful());
    }
}
