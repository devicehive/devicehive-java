package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

import static com.devicehive.websocket.model.ActionConstant.COMMAND_SUBSCRIBE;

@Data
public class CommandSubscribeAction extends RequestAction {


    @SerializedName("timestamp")
    DateTime timestamp;
    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("deviceIds")
    List<String> deviceIds;
    @SerializedName("names")
    List<String> names;
    @SerializedName("limit")
    Integer limit;


    public CommandSubscribeAction() {
        super(COMMAND_SUBSCRIBE);
    }

    @Override
    public String toString() {
        return "{\n\"CommandSubscribeAction\":{\n"
                + "\"timestamp\":" + timestamp
                + ",\n \"deviceId\":\"" + deviceId + "\""
                + ",\n \"deviceIds\":" + deviceIds
                + ",\n \"names\":" + names
                + ",\n \"limit\":\"" + limit + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
