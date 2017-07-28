package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class DeviceListResponse extends ResponseAction {
    @SerializedName("devices")
    private List<DeviceVO> devices;

    @Override
    public String toString() {
        return "{\n\"DeviceListResponse\":{\n"
                + "\"status\":\"" + getStatus() + "\""
                + ",\n \"devices\":" + devices
                + ",\n \"action\":\"" + getAction() + "\""
                + ",\n \"requestId\":\"" + getRequestId() + "\""
                + "}\n}";
    }
}
