package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ErrorAction extends Action {

    @SerializedName("code")
    int code;
    @SerializedName("error")
    String error;

    @Override
    public String toString() {
        return "{\n\"ErrorAction\":{\n"
                + " \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"code\":\"" + code + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"error\":\"" + error + "\""
                + "}\n}";
    }
}