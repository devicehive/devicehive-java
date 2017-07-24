package com.devicehive.websocket.client.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenVO extends Action {

    @SerializedName("status")
    String status;
    @SerializedName("accessToken")
    String accessToken;
    @SerializedName("refreshToken")
    String refreshToken;

    @Override
    public String toString() {
        return "{\n\"JwtTokenVO\":{\n"
                + " \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"accessToken\":\"" + accessToken + "\""
                + ",\n \"refreshToken\":\"" + refreshToken + "\""
                + "}\n}";
    }
}
