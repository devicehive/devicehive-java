package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceNotification;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class NotificationGetResponse extends ResponseAction {

    @SerializedName("notification")
    private DeviceNotification notification;

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
