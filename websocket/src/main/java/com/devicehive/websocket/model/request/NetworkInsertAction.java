package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.NetworkUpdate;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NETWORK_INSERT;

@Data
public class NetworkInsertAction extends RequestAction {

    @SerializedName("network")
    private NetworkUpdate network;

    public NetworkInsertAction() {
        super(NETWORK_INSERT);
    }
}
