package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.DEVICE_GET;

@Data
public class DeviceGetAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("commandId")
    Long commandId;

    public DeviceGetAction() {
        super(DEVICE_GET);
    }
}
