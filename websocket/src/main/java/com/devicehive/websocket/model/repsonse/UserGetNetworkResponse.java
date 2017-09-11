package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.Network;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserGetNetworkResponse extends ResponseAction {

    @SerializedName("network")
    Network network;
}
