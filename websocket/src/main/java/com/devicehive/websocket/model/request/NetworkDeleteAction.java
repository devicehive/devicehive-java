package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NETWORK_DELETE;

@Data
public class NetworkDeleteAction extends RequestAction {

    @SerializedName("id")
    private Long id;

    public NetworkDeleteAction() {
        super(NETWORK_DELETE);
    }
}
