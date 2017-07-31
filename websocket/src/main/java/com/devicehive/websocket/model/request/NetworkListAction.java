package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.NETWORK_LIST;

@Data
public class NetworkListAction extends RequestAction {

    @SerializedName("name")
    private String name;
    @SerializedName("namePattern")
    private String namePattern;
    @SerializedName("sortField")
    private String sortField;
    @SerializedName("sortOrderAsc")
    private Boolean sortOrderAsc;
    @SerializedName("take")
    private Integer take;
    @SerializedName("skip")
    private Integer skip;

    public NetworkListAction() {
        super(NETWORK_LIST);
    }
}
