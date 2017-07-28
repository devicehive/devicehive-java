package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;

public class CommandSubscribeResponse extends ResponseAction {
    @SerializedName("subscriptionId")
    String subscriptionId;

    @Override
    public String toString() {
        return "{\n\"CommandSubscribeResponse\":{\n"
                + "\"subscriptionId\":\"" + subscriptionId + "\""
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
