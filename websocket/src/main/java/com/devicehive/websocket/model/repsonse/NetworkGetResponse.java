package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.Network;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class NetworkGetResponse extends ResponseAction {

    @SerializedName("network")
    private Network network;

}
