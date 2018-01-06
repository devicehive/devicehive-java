/*
 *
 *
 *   Main.java
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

package example;

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
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    //DeviceHive settings
    private static final String accessToken = "";
    private static final String refreshToken = "";

    private static final String URL = "";

    //Constants
    private static final String SUCCESS = "SUCCESS";
    private static final String PRODUCE_NOTIFICATION = "produce_notification";
    private static final String PING = "ping";
    private static final String UNKNOWN = "unknown";
    private static final String PONG = "pong";

    //Initiating DeviceHive
    private static DeviceHive deviceHive = DeviceHive.getInstance()
            .init(URL, refreshToken, accessToken);


    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        String DEVICE_ID = "123456-example";
        //Device Initiating
        DHResponse<Device> deviceResponse = deviceHive.getDevice(DEVICE_ID);
        if (!deviceResponse.isSuccessful()) {
            System.out.println(deviceResponse);
            return;
        }
        final Device device = deviceResponse.getData();
        //Creating filter to listen commands from the server
        CommandFilter commandFilter = new CommandFilter();
        commandFilter.setCommandNames(PING);

        //Subscribing for commands
        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            @Override
            public void onSuccess(List<DeviceCommand> commands) {
                System.out.println("Command:" + commands.get(0).getCommandName());

                DeviceCommand command = commands.get(0);
                JsonObject result=new JsonObject();
                result.addProperty("status",SUCCESS);

                command.setResult(result);
                command.updateCommand();
                JsonObject params = commands.get(0).getParameters();

                if (params != null) {
                    //Getting param value

                    boolean needToSend = false;
                    if (params.has(PRODUCE_NOTIFICATION)) {
                        needToSend = params.get(PRODUCE_NOTIFICATION).getAsBoolean();
                    }

                    if (needToSend) {
                        //Sending notification that will meet filter criteria
                        device.sendNotification(PONG,
                                Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                                        String.valueOf(true))));

                        //Sending notification that won't meet filter criteria
                        device.sendNotification(UNKNOWN,
                                Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                                        String.valueOf(true))));
                    }
                }

            }

            @Override
            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        //Creating filter to listen notification from the server
        NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(PONG);

        //Subscribing for notifications
        deviceHive.subscribeNotifications(
                Collections.singletonList(DEVICE_ID),
                notificationFilter,
                new DeviceNotificationsCallback() {
                    @Override
                    public void onSuccess(List<DeviceNotification> notifications) {
                        System.out.println("Notification:" + notifications.get(0).getNotification());
                    }

                    @Override
                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //Sending command that will meet filter criteria
                device.sendCommand(PING, Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                        String.valueOf(true))));

                //Sending command that won't meet filter criteria
                device.sendCommand(UNKNOWN, Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                        String.valueOf(true))));
            }
        }, 5, 10, TimeUnit.SECONDS);
    }

}
