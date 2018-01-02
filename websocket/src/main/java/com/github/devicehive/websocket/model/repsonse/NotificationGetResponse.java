/*
 *
 *
 *   NotificationGetResponse.java
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

package com.github.devicehive.websocket.model.repsonse;

import com.github.devicehive.rest.model.DeviceNotification;
import com.google.gson.annotations.SerializedName;

public class NotificationGetResponse extends ResponseAction {

    @SerializedName("notification")
    private DeviceNotification notification;

    public DeviceNotification getNotification() {
        return notification;
    }

    public void setNotification(DeviceNotification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "{\n\"NotificationGetResponse\":{\n"
                + "\"notification\":" + notification
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
