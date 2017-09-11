package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.NetworkId;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class NetworkInsertResponse extends ResponseAction {

    @SerializedName("network")
    private NetworkId network;

}
