package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceDeleteAction extends RequestAction {

    public static final String DEVICE_DELETE = "device/delete";
    @SerializedName("deviceId")
    String deviceId;

    public DeviceDeleteAction(){
        super(DEVICE_DELETE);
    }

    @Override
    public String toString() {
        return super.toString()+"{\n\"DeviceDeleteAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
