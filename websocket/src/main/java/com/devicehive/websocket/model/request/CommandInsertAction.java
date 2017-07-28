package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.DeviceCommandWrapper;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CommandInsertAction extends RequestAction {

    public static final String COMMAND_INSERT = "command/insert";

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("command")
    DeviceCommandWrapper command;

    public CommandInsertAction() {
        super(COMMAND_INSERT);
    }

    @Override
    public String toString() {
        return "{\n\"CommandInsertAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"command\":" + command
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
