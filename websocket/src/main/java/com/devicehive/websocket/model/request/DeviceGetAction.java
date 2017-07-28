package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceGetAction extends RequestAction {
    public static final String DEVICE_GET = "device/get";
    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("commandId")
    Long commandId;

    public DeviceGetAction() {
        super(DEVICE_GET);
    }
}
