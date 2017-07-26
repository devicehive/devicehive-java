package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class DeviceListResponse extends Action {
    @SerializedName("devices")
    private List<DeviceVO> devices;

    @Override
    public String toString() {
        return "{\n\"DeviceListResponse\":{\n"
                + "\"status\":\"" + status + "\""
                + ",\n \"devices\":" + devices
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
