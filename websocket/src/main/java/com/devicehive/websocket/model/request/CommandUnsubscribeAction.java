package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class CommandUnsubscribeAction extends RequestAction {
    public static final String COMMAND_UNSUBSCRIBE = "command/unsubscribe";

    @SerializedName("deviceIds")
    List<String> deviceIds;
    @SerializedName("subscriptionId")
    String subscriptionId;


    public CommandUnsubscribeAction() {
        super(COMMAND_UNSUBSCRIBE);
    }

    @Override
    public String toString() {
        return "{\n\"CommandUnsubscribeAction\":{\n"
                + "\"deviceIds\":" + deviceIds
                + ",\n \"subscriptionId\":\"" + subscriptionId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
