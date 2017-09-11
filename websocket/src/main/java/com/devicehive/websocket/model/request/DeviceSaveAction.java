package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.DEVICE_SAVE;

@Data
public class DeviceSaveAction extends RequestAction {


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
