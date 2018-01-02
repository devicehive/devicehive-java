/*
 *
 *
 *   DeviceInterface.java
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

package com.github.devicehive.client.service;

import com.github.devicehive.client.model.*;
import com.github.devicehive.client.model.*;
import org.joda.time.DateTime;

import java.util.List;

/**
 * |-- save()  save current state to server
 * |-- getCommands(start_timestamp, end_timestamp, max_ number)
 * |     returns list of DeviceCommand objects
 * |-- getNotifications(start_timestamp, end_timestamp, max_ number)
 * |     returns list of DeviceNotification objects
 * |-- sendCommand(command, parameters, result_callback) returns DeviceCommand object
 * |-- sendNotification(name, parameters)
 * |-- subscribeCommands(callback, commandFilter), callback_proto(deviceCommand)
 * |-- subscribeNotifications(callback, nameFilter), callback_proto(deviceNotification)
 * |-- unsubscribeCommands(commandFilter)
 * -- unsubscribeNotifications(nameFilter)
 */
interface DeviceInterface {

    void save();

    List<DeviceCommand> getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber);

    List<DeviceNotification> getNotifications(DateTime startTimestamp, DateTime endTimestamp);

    DHResponse<DeviceCommand> sendCommand(String command, List<Parameter> parameters);

    DHResponse<DeviceNotification> sendNotification(String notification, List<Parameter> parameters);

    void subscribeCommands(CommandFilter commandFilter, DeviceCommandsCallback commandsCallback);

    void subscribeNotifications(NotificationFilter notificationFilter, DeviceNotificationsCallback notificationCallback);

    void unsubscribeCommands(CommandFilter commandFilter);

    void unsubscribeNotifications(NotificationFilter notificationFilter);
}
