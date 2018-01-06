/*
 *
 *
 *   DeviceNotificationApiTest.java
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

import com.github.devicehive.rest.api.DeviceNotificationApi;
import com.github.devicehive.rest.model.DeviceNotification;
import com.github.devicehive.rest.model.DeviceNotificationWrapper;
import com.github.devicehive.rest.model.NotificationInsert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import retrofit2.Response;

public class DeviceNotificationApiTest extends Helper {
    private static final String NOTIFICATION_NAME = "HTTP TEST NOTIFICATION";
    private static final String TEST_PROP = "testProp";
    private static final String TEST_VALUE = "testValue";
    private static final String UPDATED_NOTIFICATION_NAME = "UPDATED NOTIFICATION";

    private DeviceNotificationWrapper getNotificationWrapper() {
        DeviceNotificationWrapper deviceNotificationWrapper = new DeviceNotificationWrapper();
        deviceNotificationWrapper.setNotification(NOTIFICATION_NAME);

        JsonObject data = new JsonObject();
        data.addProperty(TEST_PROP, TEST_VALUE);
        JsonObject params = new JsonObject();
        params.addProperty("customData",new Gson().toJson(data));
        deviceNotificationWrapper.setParameters(params);
        return deviceNotificationWrapper;
    }

    @Test
    public void insertNotification() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceNotificationWrapper deviceNotificationWrapper = getNotificationWrapper();
        DeviceNotificationApi notificationApi = client.createService(DeviceNotificationApi.class);

        Response<NotificationInsert> response = notificationApi.insert(deviceId, deviceNotificationWrapper).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDevices(deviceId));
    }

    @Test
    public void getNotification() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceNotificationWrapper deviceNotificationWrapper = getNotificationWrapper();
        DeviceNotificationApi notificationApi = client.createService(DeviceNotificationApi.class);

        Response<NotificationInsert> response = notificationApi.insert(deviceId, deviceNotificationWrapper).execute();
        Assert.assertTrue(response.body() != null);
        NotificationInsert notification = response.body();
        assert notification != null;
        Long notificationId = notification.getId();
        Assert.assertTrue(notificationId != null);

        Response<DeviceNotification> getResponse = notificationApi.get(deviceId, notificationId).execute();
        DeviceNotification getNotification = getResponse.body();
        Assert.assertTrue(getResponse.isSuccessful());
        assert getNotification != null;
        Assert.assertTrue(getNotification.getId().equals(notification.getId()));
        Assert.assertTrue(deleteDevices(deviceId));
    }

    @Test
    public void pollNotification() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceNotificationWrapper deviceNotificationWrapper = getNotificationWrapper();
        DeviceNotificationApi notificationApi = client.createService(DeviceNotificationApi.class);

        int notificationPollAmount = 5;
        DateTime currentTimestamp = DateTime.now();
        for (int j = 0; j < notificationPollAmount; ++j) {
            notificationApi.insert(deviceId, deviceNotificationWrapper).execute();
        }
        String timestamp = currentTimestamp.withMillis(0).toString();

        Response<List<DeviceNotification>> pollResponse = notificationApi.poll(deviceId, NOTIFICATION_NAME, timestamp,
                30L).execute();
        Assert.assertTrue(pollResponse.isSuccessful());
        Assert.assertEquals(notificationPollAmount, pollResponse.body().size());
        Assert.assertTrue(deleteDevices(deviceId));
    }

    @Test
    public void pollManyNotification() throws IOException {
        String firstDeviceId = UUID.randomUUID().toString();
        String secondDeviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean firstDeviceCreated = createDevice(firstDeviceId);
        boolean secondDeviceCreated = createDevice(secondDeviceId);
        Assert.assertTrue(authenticated && firstDeviceCreated && secondDeviceCreated);

        DeviceNotificationWrapper deviceNotificationWrapper = getNotificationWrapper();
        DeviceNotificationApi notificationApi = client.createService(DeviceNotificationApi.class);

        int notificationPollAmount = 5;
        DateTime currentTimestamp = DateTime.now();
        for (int j = 0; j < notificationPollAmount; ++j) {
            notificationApi.insert(firstDeviceId, deviceNotificationWrapper).execute();
            notificationApi.insert(secondDeviceId, deviceNotificationWrapper).execute();
        }
        String timestamp = currentTimestamp.withMillis(0).toString();

        String deviceIds = firstDeviceId + "," + secondDeviceId;
        Response<List<DeviceNotification>> pollResponse = notificationApi.pollMany(deviceIds,
                NOTIFICATION_NAME, timestamp, 30L).execute();
        Assert.assertTrue(pollResponse.isSuccessful());
        Assert.assertEquals(2 * notificationPollAmount, pollResponse.body().size());
        Assert.assertTrue(deleteDevices(firstDeviceId, secondDeviceId));
    }

    @Test
    public void queryNotification() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceNotificationWrapper deviceNotificationWrapper = getNotificationWrapper();
        DeviceNotificationApi notificationApi = client.createService(DeviceNotificationApi.class);

        int notificationQueryAmount = 5;
        DateTime currentTimestamp = DateTime.now();
        for (int j = 0; j < notificationQueryAmount; ++j) {
            notificationApi.insert(deviceId, deviceNotificationWrapper).execute();
        }
        String startTimestamp = currentTimestamp.withMillis(0).toString();
        String endTimestamp = currentTimestamp.plusMinutes(2).toString();
        Response<List<DeviceNotification>> queryResponse = notificationApi.query(deviceId, startTimestamp, endTimestamp,
                NOTIFICATION_NAME, null, null, 2 * notificationQueryAmount, 0).execute();
        Assert.assertTrue(queryResponse.isSuccessful());
        Assert.assertEquals(notificationQueryAmount, queryResponse.body().size());
        Assert.assertTrue(deleteDevices(deviceId));
    }
}
