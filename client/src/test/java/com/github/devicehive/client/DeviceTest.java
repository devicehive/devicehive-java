/*
 *
 *
 *   DeviceTest.java
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

package com.github.devicehive.client;

import com.github.devicehive.client.model.*;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceTest {


    private static final String URL = "***REMOVED***";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String NOTIFICATION_A = "notificationA";
    private static final String NOTIFICATION_B = "notificationB";
    private static final String NOTIFICATION_Z = "notificationZ";
    private static final String COM_A = "comA";
    private static final String COM_B = "comB";
    private static final String COM_Z = "comZ";
    private String accessToken = "***REMOVED***";
    private String refreshToken = "***REMOVED***";

    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));


    @Test
    public void createDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        Device device = deviceHive.getDevice(deviceId);
        Assert.assertTrue(device != null);
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }

    @Test
    public void getCommands() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        final Device device = deviceHive.getDevice(deviceId);
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
        String deviceId = UUID.randomUUID().toString();
        final Device device = deviceHive.getDevice(deviceId);
        final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        final String commandName1 = COM_A + new Random().nextInt();
        final String commandName2 = COM_B + new Random().nextInt();
        final String commandName3 = COM_Z + new Random().nextInt();

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
        }), 5, TimeUnit.SECONDS);
        final CountDownLatch latch = new CountDownLatch(3);


        final CommandFilter commandFilter = new CommandFilter();

        commandFilter.setCommandNames(commandName1, commandName2);
        commandFilter.setStartTimestamp(DateTime.now());
        commandFilter.setEndTimestamp(DateTime.now().plusMinutes(2));
        commandFilter.setMaxNumber(30);

        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            public void onSuccess(List<DeviceCommand> commands) {
                for (DeviceCommand command : commands) {
                    if (command.getCommandName().equals(commandName1)) {
                        latch.countDown();
                    } else if (command.getCommandName().equals(commandName2)) {
                        latch.countDown();
                        commandFilter.setCommandNames(commandName3);
                        device.unsubscribeCommands(commandFilter);

                        service.schedule(new Thread(new Runnable() {
                            public void run() {
                                List<Parameter> parameters = new ArrayList<>();
                                parameters.add(new Parameter("Param 1", "Value 1"));
                                parameters.add(new Parameter("Param 3", "Value 3"));
                                device.sendCommand(commandName3, parameters);
                            }
                        }), 3, TimeUnit.SECONDS);

                    } else if ((command.getCommandName().equals(commandName3))) {
                        latch.countDown();
                        return;
                    }


                }
            }

            public void onFail(FailureData failureData) {
            }
        });

        latch.await(20, TimeUnit.SECONDS);
        Assert.assertTrue(latch.getCount() == 0);
        device.unsubscribeAllCommands();
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }

    @Test
    public void subscribeNotifications() throws IOException, InterruptedException {
        String deviceId = UUID.randomUUID().toString();
        final Device device = deviceHive.getDevice(deviceId);
        final CountDownLatch latch = new CountDownLatch(3);
        final String notificationName1 = NOTIFICATION_A + new Random().nextInt();
        final String notificationName2 = NOTIFICATION_B + new Random().nextInt();
        final String notificationName3 = NOTIFICATION_Z + new Random().nextInt();

        final NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(notificationName1, notificationName2);
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));

        device.subscribeNotifications(notificationFilter, new DeviceNotificationsCallback() {

            public void onSuccess(List<DeviceNotification> notifications) {
                for (DeviceNotification notification :
                        notifications) {
                    if (notification.getNotification().equals(notificationName1)) {
                        latch.countDown();
                        Assert.assertTrue(true);
                    } else if (notification.getNotification().equals(notificationName2)) {
                        Assert.assertTrue(true);
                        latch.countDown();
                    } else if ((notification.getNotification().equals(notificationName3))) {
                        Assert.assertTrue(true);
                        latch.countDown();
                    }
                }
            }

            public void onFail(FailureData failureData) {
            }
        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                notificationFilter.setNotificationNames(notificationName3);
                device.unsubscribeNotifications(notificationFilter);
            }
        }), 2, TimeUnit.SECONDS);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification(notificationName3, parameters);
            }
        }), 5, TimeUnit.SECONDS);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification(notificationName1, parameters);
                device.sendNotification(notificationName2, parameters);
            }
        }), 7, TimeUnit.SECONDS);
        latch.await(20, TimeUnit.SECONDS);
        Assert.assertTrue(latch.getCount() == 0);
        Assert.assertTrue(deviceHive.removeDevice(deviceId).isSuccessful());
    }


    @Test
    public void getNotification() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        final Device device = deviceHive.getDevice(deviceId);
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
    public void sendNotification() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        Device device = deviceHive.getDevice(deviceId);

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
