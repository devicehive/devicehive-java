package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class NotificationSubscribeResponse extends ResponseAction {

    @SerializedName("subscriptionId")
    private String subscriptionId;

    @Override
    public String toString() {
        return "{\n\"NotificationSubscribeResponse\":{\n"
                + "\"subscriptionId\":\"" + subscriptionId + "\""
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
