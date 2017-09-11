package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_GET;

@Data
public class UserGetAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;

    public UserGetAction() {
        super(USER_GET);
    }
}
