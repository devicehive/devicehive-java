package com.devicehive.rest;

import com.devicehive.rest.api.DeviceNotificationApi;
import com.devicehive.rest.model.DeviceNotification;
import com.devicehive.rest.model.DeviceNotificationWrapper;
import com.devicehive.rest.model.NotificationInsert;
import com.devicehive.rest.model.JsonStringWrapper;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DeviceNotificationApiTest extends Helper {
    private static final String NOTIFICATION_NAME = "TEST NOTIFICATION";
    private static final String TEST_PROP = "testProp";
    private static final String TEST_VALUE = "testValue";
    private static final String UPDATED_NOTIFICATION_NAME = "UPDATED NOTIFICATION";

    private DeviceNotificationWrapper getNotificationWrapper() {
        DeviceNotificationWrapper deviceNotificationWrapper = new DeviceNotificationWrapper();
        deviceNotificationWrapper.setNotification(NOTIFICATION_NAME);

        JSONObject data = new JSONObject();
        data.put(TEST_PROP, TEST_VALUE);
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString(new Gson().toJson(data));
        deviceNotificationWrapper.setParameters(jsonStringWrapper);
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
        Response<List<DeviceNotification>> pollResponse = notificationApi.pollMany(30L, deviceIds,
                NOTIFICATION_NAME, timestamp).execute();
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
