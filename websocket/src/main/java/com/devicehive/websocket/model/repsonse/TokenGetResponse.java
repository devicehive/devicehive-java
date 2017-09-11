package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenGetResponse extends ResponseAction {

    @SerializedName("accessToken")
    String accessToken;
    @SerializedName("refreshToken")
    String refreshToken;

    @Override
    public String toString() {
        return "{\n\"TokenGetResponse\":{\n"
                + " \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"accessToken\":\"" + accessToken + "\""
                + ",\n \"refreshToken\":\"" + refreshToken + "\""
                + "}\n}";
    }
}
