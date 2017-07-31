package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.TOKEN;

@Data
public class TokenAction extends RequestAction {

    @SerializedName("login")
    String login;
    @SerializedName("password")
    String password;

    public TokenAction() {
        super(TOKEN);
    }
}
