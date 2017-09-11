package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.DeviceNotificationWrapper;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NOTIFICATION_INSERT;

@Data
public class NotificationInsertAction extends RequestAction {

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("notification")
    private DeviceNotificationWrapper notification;

    public NotificationInsertAction() {
        super(NOTIFICATION_INSERT);
    }
}
