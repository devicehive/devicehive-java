package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.DEVICE_DELETE;

@Data
public class DeviceDeleteAction extends RequestAction {


    @SerializedName("deviceId")
    String deviceId;

    public DeviceDeleteAction() {
        super(DEVICE_DELETE);
    }

    @Override
    public String toString() {
        return super.toString() + "{\n\"DeviceDeleteAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
