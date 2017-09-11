package com.devicehive.websocket.model.repsonse.data;

import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.google.gson.annotations.SerializedName;

public class NetworkId extends ResponseAction {

    @SerializedName("id")
    private Long id;
}
