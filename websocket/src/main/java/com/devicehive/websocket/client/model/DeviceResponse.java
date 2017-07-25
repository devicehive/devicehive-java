package com.devicehive.websocket.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceResponse extends Action {
//    {
//        "action": {string},
//        "requestId": {object},
//        "status": {string},
//        "devices": [{
//        "id": {string},
//        "name": {string},
//        "status": {string},
//        "data": {object},
//        "networkId": {integer},
//        "isBlocked": {boolean},
//    }]
//    }

    @SerializedName("status")
    private String status;
    @SerializedName("devices")
    private List<DeviceVO> devices;

    @Override
    public String toString() {
        return "{\n\"DeviceResponse\":{\n"
                + "\"status\":\"" + status + "\""
                + ",\n \"devices\":" + devices
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
