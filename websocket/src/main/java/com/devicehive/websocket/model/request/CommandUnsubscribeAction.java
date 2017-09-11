package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.COMMAND_UNSUBSCRIBE;

@Data
public class CommandUnsubscribeAction extends RequestAction {


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
