package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data
public class AuthAction extends Action {

    @SerializedName("token")
    String token;
}
