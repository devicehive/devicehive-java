package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceListAction extends RequestAction {
    public static final String DEVICE_LIST = "device/list";
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
        super(DEVICE_LIST);
    }
}
