package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.JwtPayload;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.TOKEN_CREATE;

@Data
public class TokenCreateAction extends RequestAction {

    @SerializedName("payload")
    private JwtPayload payload;

    public TokenCreateAction() {
        super(TOKEN_CREATE);
    }

    @Override
    public String toString() {
        return "{\n\"TokenCreateAction\":{\n"
                + "\"payload\":" + payload
                + ",\n \"requestId\":\"" + requestId + "\""
                + ",\n \"actionId\":\"" + getAction() + "\""
                + "}\n}";
    }
}
