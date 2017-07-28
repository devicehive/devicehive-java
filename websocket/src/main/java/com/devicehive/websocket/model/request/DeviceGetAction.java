package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceGetAction extends RequestAction {
    @SerializedName("deviceId")
    String deviceId;

    public DeviceGetAction() {
        super("device/get");
    }
}
