package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NOTIFICATION_GET;

@Data
public class NotificationGetAction extends RequestAction {

    @SerializedName("notificationId")
    private Long notificationId;

    @SerializedName("deviceId")
    private String deviceId;


    public NotificationGetAction() {
        super(NOTIFICATION_GET);
    }
}
