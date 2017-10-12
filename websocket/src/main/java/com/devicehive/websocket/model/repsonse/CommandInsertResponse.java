package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceCommand;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CommandInsertResponse extends ResponseAction {

    @SerializedName("command")
    DeviceCommand command;
    @SerializedName("subscriptionId")
    private Long subscriptionId;

    @Override
    public String toString() {
        return "{\n\"CommandInsertResponse\":{\n"
                + "\"command\":" + command
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
