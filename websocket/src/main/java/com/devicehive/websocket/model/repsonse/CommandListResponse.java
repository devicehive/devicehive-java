package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceCommand;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class CommandListResponse extends ResponseAction {

    @SerializedName("commands")
    List<DeviceCommand> commands;

    @Override
    public String toString() {
        return "{\n\"CommandListResponse\":{\n"
                + "\"commands\":" + commands
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
