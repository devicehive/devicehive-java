package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenAction extends RequestAction {
    public static final String TOKEN = "token";

    @SerializedName("login")
    String login;
    @SerializedName("password")
    String password;

    public TokenAction() {
        super(TOKEN);
    }
}
