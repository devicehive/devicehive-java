package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_UNASSIGN_NETWORK;

@Data
public class UserUnassignNetworkAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;
    @SerializedName("networkId")
    private Long networkId;

    public UserUnassignNetworkAction() {
        super(USER_UNASSIGN_NETWORK);
    }
}
