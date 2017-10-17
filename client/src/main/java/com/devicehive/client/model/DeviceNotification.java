/*
 *
 *
 *   DeviceNotification.java
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

package com.devicehive.client.model;

import com.devicehive.rest.model.JsonStringWrapper;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Builder
public class DeviceNotification {
    @Getter
    private Long id = null;

    @Getter
    private String notification = null;

    @Getter
    private String deviceId = null;

    @Getter
    private DateTime timestamp = null;

    @Getter
    private JsonStringWrapper parameters = null;

    public static DeviceNotification create(com.devicehive.rest.model.DeviceNotification notification) {
        return DeviceNotification.builder()
                .id(notification.getId())
                .notification(notification.getNotification())
                .deviceId(notification.getDeviceId())
                .timestamp(notification.getTimestamp())
                .parameters(new JsonStringWrapper(notification.getParameters().getJsonString()))
                .build();
    }
    public static DeviceNotification create(com.devicehive.websocket.model.repsonse.data.DeviceNotification notification) {
        return DeviceNotification.builder()
                .id(notification.getId())
                .notification(notification.getNotification())
                .deviceId(notification.getDeviceId())
                .timestamp(notification.getTimestamp())
                .parameters(notification.getParameters())
                .build();
    }

    public static List<DeviceNotification> createList(List<com.devicehive.rest.model.DeviceNotification> notifications) {
        if (notifications == null) {
            return null;
        }
        List<DeviceNotification> result = new ArrayList<DeviceNotification>(notifications.size());
        for (com.devicehive.rest.model.DeviceNotification n : notifications) {
            result.add(create(n));
        }
        return result;
    }

    public static List<DeviceNotification> createListFromWS(List<com.devicehive.websocket.model.repsonse.data.DeviceNotification> notifications) {
        if (notifications == null) {
            return null;
        }
        List<DeviceNotification> result = new ArrayList<DeviceNotification>(notifications.size());
        for (com.devicehive.websocket.model.repsonse.data.DeviceNotification n : notifications) {
            result.add(create(n));
        }
        return result;
    }


}
