package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenRefreshResponse extends ResponseAction {

    @SerializedName("accessToken")
    private String accessToken;

    @Override
    public String toString() {
        return "{\n\"TokenRefreshResponse\":{\n"
                + "\"accessToken\":\"" + accessToken + "\""
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }
}
