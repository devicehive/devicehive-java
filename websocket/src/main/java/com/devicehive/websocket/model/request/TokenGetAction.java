package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.TOKEN_GET;

@Data
public class TokenGetAction extends RequestAction {

    @SerializedName("login")
    String login;
    @SerializedName("password")
    String password;

    public TokenGetAction() {
        super(TOKEN_GET);
    }
}
