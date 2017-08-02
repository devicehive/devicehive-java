package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_ASSIGN_NETWORK;

@Data
public class UserAssignNetworkAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;
    @SerializedName("networkId")
    private Long networkId;

    public UserAssignNetworkAction() {
        super(USER_ASSIGN_NETWORK);
    }
}
