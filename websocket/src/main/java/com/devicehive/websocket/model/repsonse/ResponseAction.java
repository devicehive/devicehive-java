package com.devicehive.websocket.model.repsonse;

import com.google.gson.annotations.SerializedName;

public class ResponseAction {

    @SerializedName("action")
    String action;
    @SerializedName("requestId")
    Long requestId;
    @SerializedName("status")
    String status;

    public String getAction() {
        return action;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "{\n\"ResponseAction\":{\n"
                + "\"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"status\":\"" + status + "\""
                + "}\n}";
    }

    public boolean compareAction(String actionName){
        return action.equalsIgnoreCase(actionName);
    }
}
