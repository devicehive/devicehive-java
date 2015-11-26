package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

public class NotiMapEl {

    @SerializedName("deviceGuid")
    private String guid = null;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
