/*
 *
 *
 *   DeviceNotification.java
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

package com.github.devicehive.client.model;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class DeviceNotification {
    private Long id;

    private String notification;

    private String deviceId;

    private Long networkId;

    private DateTime timestamp;

    private JsonObject parameters = new JsonObject();

    private DeviceNotification() {
    }

    private DeviceNotification(Long id, String notification, String deviceId, Long networkId, DateTime timestamp, JsonObject parameters) {
        this.id = id;
        this.notification = notification;
        this.deviceId = deviceId;
        this.networkId = networkId;
        this.timestamp = timestamp;
        this.parameters = parameters == null ? new JsonObject() : parameters;
    }

    public Long getId() {
        return id;
    }

    public String getNotification() {
        return notification;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public JsonObject getParameters() {
        return parameters;
    }

    public static DeviceNotification create(com.github.devicehive.rest.model.DeviceNotification notification) {
        return new Builder()
                .id(notification.getId())
                .notification(notification.getNotification())
                .deviceId(notification.getDeviceId())
                .networkId(notification.getNetworkId())
                .timestamp(notification.getTimestamp())
                .parameters(notification.getParameters())
                .build();
    }

    public static DeviceNotification create(DeviceNotification notification) {
        return new Builder()
                .id(notification.getId())
                .notification(notification.getNotification())
                .deviceId(notification.getDeviceId())
                .networkId(notification.getNetworkId())
                .timestamp(notification.getTimestamp())
                .parameters(notification.getParameters())
                .build();
    }

    public static List<DeviceNotification> createList(List<com.github.devicehive.rest.model.DeviceNotification> notifications) {
        if (notifications == null) {
            return null;
        }
        List<DeviceNotification> result = new ArrayList<DeviceNotification>(notifications.size());
        for (com.github.devicehive.rest.model.DeviceNotification n : notifications) {
            result.add(create(n));
        }
        return result;
    }

    public static List<DeviceNotification> createListFromWS(List<com.github.devicehive.rest.model.DeviceNotification> notifications) {
        if (notifications == null) {
            return null;
        }
        List<DeviceNotification> result = new ArrayList<DeviceNotification>(notifications.size());
        for (com.github.devicehive.rest.model.DeviceNotification n : notifications) {
            result.add(create(n));
        }
        return result;
    }

    @Override
    public String toString() {
        return "{\n\"DeviceNotification\":{\n"
                + "\"id\":\"" + id + "\""
                + ",\n \"notification\":\"" + notification + "\""
                + ",\n \"deviceId\":\"" + deviceId + "\""
                + ",\n \"networkId\":\"" + networkId + "\""
                + ",\n \"timestamp\":" + timestamp
                + ",\n \"parameters\":" + parameters
                + "}\n}";
    }

    static class Builder {
        private Long id;
        private String notification;
        private String deviceId;
        private Long networkId;
        private DateTime timestamp;
        private JsonObject parameters = new JsonObject();

        Builder id(Long id) {
            this.id = id;
            return this;
        }

        Builder notification(String notification) {
            this.notification = notification;
            return this;
        }

        Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        Builder networkId(Long networkId) {
            this.networkId = networkId;
            return this;
        }

        Builder timestamp(DateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        Builder parameters(JsonObject parameters) {
            if (parameters == null) {
                return this;
            }
            this.parameters = parameters;
            return this;
        }

        DeviceNotification build() {
            return new DeviceNotification(id, notification, deviceId, networkId, timestamp, parameters);
        }
    }
}
