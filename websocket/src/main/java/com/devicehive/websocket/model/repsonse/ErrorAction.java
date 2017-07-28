package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ErrorAction extends ResponseAction {
    public static final String ERROR="error";

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