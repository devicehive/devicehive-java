package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceNotification;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class NotificationListResponse extends ResponseAction {

    @SerializedName("notifications")
    private List<DeviceNotification> notifications;

    @Override
    public String toString() {
        return "{\n\"NotificationListResponse\":{\n"
                + "\"notifications\":" + notifications
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
