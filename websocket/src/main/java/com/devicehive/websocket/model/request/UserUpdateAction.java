package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.UserUpdate;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_UPDATE;

@Data
public class UserUpdateAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;
    @SerializedName("user")
    private UserUpdate user;

    public UserUpdateAction() {
        super(USER_UPDATE);
    }
}
