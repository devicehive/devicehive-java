package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceDeleteAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;

    public DeviceDeleteAction(){
        super("device/delete");
    }

    @Override
    public String toString() {
        return super.toString()+"{\n\"DeviceDeleteAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
