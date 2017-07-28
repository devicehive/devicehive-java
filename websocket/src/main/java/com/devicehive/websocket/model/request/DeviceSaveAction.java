package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceSaveAction extends RequestAction {

    public static final String DEVICE_SAVE = "device/save";
    @SerializedName("device")
    DeviceVO device;
    public DeviceSaveAction() {
        super(DEVICE_SAVE);
    }

    @Override
    public String toString() {
        return "{\n\"DeviceSaveAction\":{\n"
                + "\"device\":" + device
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
