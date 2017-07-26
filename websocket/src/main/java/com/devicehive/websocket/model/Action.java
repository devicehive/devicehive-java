package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Action {

    @SerializedName("action")
    String action;
    @SerializedName("requestId")
    Long requestId;
    @SerializedName("status")
    String status;

}
