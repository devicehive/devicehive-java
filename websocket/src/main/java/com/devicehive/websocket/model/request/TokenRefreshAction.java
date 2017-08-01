package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.TOKEN_REFRESH;

@Data
public class TokenRefreshAction extends RequestAction {

    @SerializedName("refreshToken")
    private String refreshToken;

    public TokenRefreshAction() {
        super(TOKEN_REFRESH);
    }
}
