package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.AUTH;


@Data
public class AuthenticateAction extends RequestAction {

    @SerializedName("token")
    String token;

    public AuthenticateAction() {
        super(AUTH);
    }
}
