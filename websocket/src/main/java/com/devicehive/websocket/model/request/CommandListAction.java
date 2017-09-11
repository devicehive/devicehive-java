package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.SortOrder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.joda.time.DateTime;

import static com.devicehive.websocket.model.ActionConstant.COMMAND_LIST;

@Data
public class CommandListAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("start")
    DateTime start;
    @SerializedName("end")
    DateTime end;
    @SerializedName("command")
    String commandName;
    @SerializedName("status")
    String status;
    @SerializedName("sortOrder")
    SortOrder sortOrder;
    @SerializedName("take")
    Integer take;
    @SerializedName("skip")
    Integer skip;

    public CommandListAction() {
        super(COMMAND_LIST);
    }

    @Override
    public String toString() {
        return "{\n\"CommandListAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"start\":" + start
                + ",\n \"end\":" + end
                + ",\n \"commandName\":\"" + commandName + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"sortOrder\":\"" + sortOrder + "\""
                + ",\n \"take\":\"" + take + "\""
                + ",\n \"skip\":\"" + skip + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }


}
