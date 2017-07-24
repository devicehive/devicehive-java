package com.devicehive.websocket.client.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class Action {

    @SerializedName("action")
    String action;
    @SerializedName("requestId")
    Long requestId;

}
