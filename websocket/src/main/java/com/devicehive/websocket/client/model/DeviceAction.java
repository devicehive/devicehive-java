package com.devicehive.websocket.client.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceAction extends Action {

//    {
//        "action": {string},
//        "requestId": {object},
//        "name": {string},
//        "namePattern": {string},
//        "networkId": {long},
//        "networkName": {string},
//        "sortField": {string},
//        "sortOrder": {string},
//        "take": {integer},
//        "skip": {integer}
//    }


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

}
