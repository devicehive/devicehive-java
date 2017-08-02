package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_GET_NETWORK;

@Data
public class UserGetNetworkAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;
    @SerializedName("networkId")
    private Long networkId;

    public UserGetNetworkAction() {
        super(USER_GET_NETWORK);
    }
}
