/*
 *
 *
 *   TimerDevice.java
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

package com.github.devicehive.examples;

import com.github.devicehive.rest.ApiClient;
import com.github.devicehive.rest.api.*;
import com.github.devicehive.rest.model.*;
import com.github.devicehive.rest.utils.Const;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimerDevice {
    private DateTime alarmTime = null;
    private DateTime currentTime = null;
    static final String DEVICE_ID = "TIMER-1234";
    private ApiClient client;

    private ScheduledExecutorService ses;
    private DeviceNotificationApi notificationApiImpl;
    private DeviceApi deviceApiImpl;
    private ApiInfoApi infoApiImpl;
    private DeviceCommandApi commandApiImpl;
    private NetworkApi networkApiImpl;

    private DateTime timestamp;

    public TimerDevice(ApiClient client) {
        this.client = client;
        ses = Executors.newScheduledThreadPool(3);
        inflateApi();
    }

    private void inflateApi() {
        notificationApiImpl = client.createService(DeviceNotificationApi.class);
        commandApiImpl = client.createService(DeviceCommandApi.class);
        infoApiImpl = client.createService(ApiInfoApi.class);
        deviceApiImpl = client.createService(DeviceApi.class);
        networkApiImpl = client.createService(NetworkApi.class);
    }

    void run() throws IOException {

        registerDevice();

        ApiInfo apiInfo = infoApiImpl.getApiInfo().execute().body();

        timestamp = apiInfo.getServerTimestamp();
        //Send current timestamp notification
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                DeviceNotificationWrapper myDevice = getTimestampNotificationWrapper();
                try {
                    notificationApiImpl.insert(DEVICE_ID, myDevice).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);

        //Commands Polling
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<DeviceCommand> commands = null;
                try {
                    commands = commandApiImpl.poll(DEVICE_ID, "ON", timestamp.toString(), 30L, null)
                            .execute()
                            .body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (commands != null && commands.size() != 0) {
                    Collections.sort(commands);
                    DeviceCommand command = commands.get(commands.size() - 1);
                    updateTimestamp(command.getTimestamp());
                    JsonObject deviceParams = command.getParameters();
                    try {
                        if (deviceParams.has("timestamp")) {
                            TimeStamp timestamp = new Gson().fromJson(deviceParams.get("timestamp").getAsString(),
                                    TimeStamp.class);
                            alarmTime = DateTime.parse(timestamp.getTimestamp()).withMillisOfSecond(0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        //Send Alarm Notification
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                currentTime = DateTime.now().withMillisOfSecond(0);
                DeviceNotificationWrapper alarmNotification = createAlarmNotificationWrapper();
                if (isAlarmTime()) {
                    try {
                        notificationApiImpl.insert(DEVICE_ID, alarmNotification).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    //Adds new device or updates current device

    private void registerDevice() {
        DeviceUpdate device = createDevice();
        try {
            List<Network> networks = networkApiImpl.list(null, null, null, null, null, null).
                    execute().body();
            if (networks != null && !networks.isEmpty()) {
                device.setNetworkId(networks.get(0).getId());
                deviceApiImpl.register(device, DEVICE_ID).execute().raw().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DeviceUpdate createDevice() {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);

        return device;
    }

    private boolean isAlarmTime() {
        return alarmTime != null && alarmTime.isEqual(currentTime);
    }

    private DeviceNotificationWrapper getTimestampNotificationWrapper() {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonObject params = new JsonObject();
        DateTime currentTimestamp = DateTime.now();
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setTimestamp(currentTimestamp.toString());
        params.addProperty("timestamp", new Gson().toJson(timeStamp));
        wrapper.setParameters(params);
        wrapper.setNotification("Timestamp");
        return wrapper;
    }

    private DeviceNotificationWrapper createAlarmNotificationWrapper() {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();

        AlarmNotification notification = new AlarmNotification();
        notification.setMessage("BIP BIP BIP at " + alarmTime);

        JsonObject params = new JsonObject();
        params.addProperty("notification", new Gson().toJson(notification));
        wrapper.setParameters(params);
        wrapper.setNotification("ALARM");
        return wrapper;
    }

    private void updateTimestamp(DateTime timestamp) {
        if (this.timestamp == null) {
            this.timestamp = timestamp;
        } else if (this.timestamp.isBefore(timestamp)) {
            this.timestamp = timestamp;
        }
    }
}
