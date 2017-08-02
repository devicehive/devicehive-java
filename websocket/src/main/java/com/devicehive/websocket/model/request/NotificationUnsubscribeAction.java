package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.NOTIFICATION_UNSUBSCRIBE;
@Data
public class NotificationUnsubscribeAction extends RequestAction {
    @SerializedName("deviceIds")
    private List<String> deviceIds;
    @SerializedName("subscriptionId")
    private String subscriptionId;

    public NotificationUnsubscribeAction() {
        super(NOTIFICATION_UNSUBSCRIBE);
    }
}
