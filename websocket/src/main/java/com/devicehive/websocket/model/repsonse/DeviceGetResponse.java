package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceGetResponse extends ResponseAction {
    @SerializedName("device")
    private DeviceVO device;

    @Override
    public String toString() {
        return "{\n\"DeviceListResponse\":{\n"
                + "\"status\":\"" + status + "\""
                + ",\n \"devices\":" + device
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
