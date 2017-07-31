package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.COMMAND_UPDATE;

@Data
public class CommandUpdateAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("commandId")
    String commandId;
    @SerializedName("command")
    DeviceCommandWrapper command;

    public CommandUpdateAction() {
        super(COMMAND_UPDATE);
    }

    @Override
    public String toString() {
        return "{\n\"CommandUpdateAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"commandId\":\"" + commandId + "\""
                + ",\n \"command\":" + command
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
