package com.devicehive.client.model;

import com.devicehive.websocket.model.repsonse.data.JsonStringWrapper;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DeviceNotification {
    private Long id = null;

    private String notification = null;

    private String deviceId = null;

    private DateTime timestamp = null;

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
