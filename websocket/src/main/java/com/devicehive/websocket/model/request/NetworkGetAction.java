package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NETWORK_GET;

@Data
public class NetworkGetAction extends RequestAction {

    @SerializedName("id")
    private Long id;

    public NetworkGetAction() {
        super(NETWORK_GET);
    }
}
