package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceGetAction extends Action {
    @SerializedName("deviceId")
    String deviceId;

    public DeviceGetAction() {
        setAction("device/get");
    }
}
