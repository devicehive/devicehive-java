package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceListAction extends Action {
    @SerializedName("name")
    String name;
    @SerializedName("namePattern")
    String namePattern;
    @SerializedName("networkId")
    Long networkId;
    @SerializedName("networkName")
    String networkName;
    @SerializedName("sortField")
    String sortField;
    @SerializedName("sortOrder")
    String sortOrder;
    @SerializedName("take")
    Integer take;
    @SerializedName("skip")
    Integer skip;

    public DeviceListAction() {
        setAction("device/list");
    }
}
