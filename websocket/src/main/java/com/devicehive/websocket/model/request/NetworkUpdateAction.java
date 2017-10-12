package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.NetworkUpdate;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NETWORK_UPDATE;

@Data
public class NetworkUpdateAction extends RequestAction {

    //TODO add networkId here, remove it from body
    @SerializedName("network")
    private NetworkUpdate network;
    
    public NetworkUpdateAction() {
        super(NETWORK_UPDATE);
    }
}
