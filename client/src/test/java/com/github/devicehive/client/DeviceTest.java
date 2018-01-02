/*
 *
 *
 *   DeviceTest.java
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

import com.github.devicehive.client.model.CommandFilter;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceCommandsCallback;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.client.model.DeviceNotificationsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.NotificationFilter;
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.rest.model.JwtToken;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceTest extends Helper {


    private static final String NOTIFICATION_A = "notificationA";
    private static final String NOTIFICATION_B = "notificationB";
    private static final String COM_A = "comA";
    private static final String COM_B = "comB";
    private static final String DEVICE_PREFIX = "JAVA-LIB-";

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
    public void createDevice() {
        String deviceId = DEVICE_PREFIX + UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        Assert.assertTrue(deviceResponse.isSuccessful());
        final Device device = deviceResponse.getData();
        Assert.assertTrue(device != null);
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }

    @Test
    public void getCommands() throws IOException {
        String deviceId = DEVICE_PREFIX + UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        Assert.assertTrue(deviceResponse.isSuccessful());
        final Device device = deviceResponse.getData();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendCommand("Command TEST", parameters);
            }
        }), 5, TimeUnit.SECONDS);

        List<DeviceCommand> list =
                device.getCommands(DateTime.now(), DateTime.now().plusMinutes(1), 30);
        Assert.assertTrue(list.size() > 0);
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }

    @Test
    public void subscribeCommands() throws InterruptedException {
        String deviceId = DEVICE_PREFIX + UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        Assert.assertTrue(deviceResponse.isSuccessful());
        final Device device = deviceResponse.getData();
        final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        final String commandName1 = COM_A;
        final String commandName2 = COM_B;

        //Prepare Command A and Command B
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();
                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendCommand(commandName1, parameters);
                device.sendCommand(commandName2, parameters);
            }
        }), 3, TimeUnit.SECONDS);

        final CountDownLatch latch = new CountDownLatch(1);
        final CommandFilter commandFilter = new CommandFilter();

        commandFilter.setCommandNames(commandName1);

        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            public void onSuccess(List<DeviceCommand> commands) {
                for (DeviceCommand command : commands) {
                    if (Objects.equals(command.getCommandName(), commandName1)) {
                        latch.countDown();
                    } else if (Objects.equals(command.getCommandName(), commandName2)) {
                        Assert.assertTrue(false);
                    }


                }
            }

            public void onFail(FailureData failureData) {
                for (int i = 0; i < latch.getCount(); i++) {
                    latch.countDown();
                }
                Assert.assertTrue(false);
            }
        });

        latch.await(10, TimeUnit.SECONDS);
        Assert.assertTrue(latch.getCount() == 0);
        device.unsubscribeAllCommands();
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }

    @Test
    public void subscribeNotifications() throws InterruptedException {
        String deviceId = DEVICE_PREFIX + UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        Assert.assertTrue(deviceResponse.isSuccessful());

        final Device device = deviceResponse.getData();
        final CountDownLatch latch = new CountDownLatch(1);

        final String notificationName1 = NOTIFICATION_A;
        final String notificationName2 = NOTIFICATION_B;

        NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(notificationName1);

        device.subscribeNotifications(notificationFilter, new DeviceNotificationsCallback() {
            public void onSuccess(List<DeviceNotification> notifications) {
                for (DeviceNotification notification : notifications) {
                    if (Objects.equals(notification.getNotification(), notificationName1)) {
                        latch.countDown();
                    } else if (Objects.equals(notification.getNotification(), notificationName2)) {
                        latch.countDown();
                        Assert.assertTrue(false);
                    }

                }
            }

            public void onFail(FailureData failureData) {
                for (int i = 0; i < latch.getCount(); i++) {
                    latch.countDown();
                }
                Assert.assertTrue(false);
            }
        });

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();
                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification(notificationName2, parameters);
                device.sendNotification(notificationName1, parameters);

            }
        }), 2, TimeUnit.SECONDS);

        latch.await(15, TimeUnit.SECONDS);
        Assert.assertTrue(latch.getCount() == 0);
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }


    @Test
    public void getNotification() {
        String deviceId = UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        Assert.assertTrue(deviceResponse.isSuccessful());
        final Device device = deviceResponse.getData();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification("NOTIFICATION MESSAGE", parameters);
            }
        }), 5, TimeUnit.SECONDS);

        List<DeviceNotification> response = device.getNotifications(DateTime.now(), DateTime.now().plusMinutes(1));
        Assert.assertTrue(response.size() > 0);
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }

    @Test
    public void sendNotification() {
        String deviceId = DEVICE_PREFIX + UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        Assert.assertTrue(deviceResponse.isSuccessful());
        final Device device = deviceResponse.getData();
        Assert.assertNotNull(device);
        List<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new Parameter("Param 1", "Value 1"));
        parameters.add(new Parameter("Param 2", "Value 2"));
        parameters.add(new Parameter("Param 3", "Value 3"));
        parameters.add(new Parameter("Param 4", "Value 4"));

        DHResponse<DeviceNotification> response = device.sendNotification("NOTIFICATION MESSAGE", parameters);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }


}
