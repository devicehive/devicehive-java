package com.devicehive.client.model;

import com.devicehive.rest.model.JsonStringWrapper;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

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
                .parameters(notification.getParameters())
                .build();
    }


}
