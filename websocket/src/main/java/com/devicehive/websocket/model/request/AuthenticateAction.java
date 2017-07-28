package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data
public class AuthenticateAction extends RequestAction {
    public static final String AUTH = "authenticate";
    @SerializedName("token")
    String token;

    public AuthenticateAction() {
        super(AUTH);
    }
}
