package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenAction extends Action{

    @SerializedName("login")
    String login;
    @SerializedName("password")
    String password;

    public TokenAction() {
        setAction("token");
    }
}
