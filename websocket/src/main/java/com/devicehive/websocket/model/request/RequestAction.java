package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;


public class RequestAction {

    @SerializedName("action")
    private String action;
    @SerializedName("requestId")
    Long requestId;

    public RequestAction(String action) {
        this.action = action;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }


    @Override
    public String toString() {
        return "{\n\"RequestAction\":{\n"
                + "\"action\":\"" + action + "\""
                + "}\n}";
    }
}
