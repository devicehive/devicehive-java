package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ErrorResponse extends ResponseAction {
    public static final String ERROR="error";

    @SerializedName("code")
    int code;
    @SerializedName("error")
    String error;

    @Override
    public String toString() {
        return "{\n\"ErrorResponse\":{\n"
                + " \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"code\":\"" + code + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"error\":\"" + error + "\""
                + "}\n}";
    }
}