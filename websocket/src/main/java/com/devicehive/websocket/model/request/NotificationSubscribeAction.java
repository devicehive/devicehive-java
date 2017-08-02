package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.NOTIFICATION_SUBSCRIBE;

@Data
public class NotificationSubscribeAction extends RequestAction {

    @SerializedName("timestamp")
    private DateTime timestamp;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("deviceIds")
    private List<String> deviceIds;
    @SerializedName("names")
    private List<String> names;


    public NotificationSubscribeAction() {
        super(NOTIFICATION_SUBSCRIBE);
    }
}
