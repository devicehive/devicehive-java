package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceCommand;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CommandGetResponse extends ResponseAction {

    @SerializedName("command")
    DeviceCommand command;

}
