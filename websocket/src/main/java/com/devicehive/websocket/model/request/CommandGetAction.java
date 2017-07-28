package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CommandGetAction extends RequestAction {

    public static final String COMMAND_GET = "command/get";

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("commandId")
    String commandId;

    public CommandGetAction() {
        super(COMMAND_GET);
    }

    @Override
    public String toString() {
        return "{\n\"CommandGetAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"commandId\":\"" + commandId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
