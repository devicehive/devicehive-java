package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_DELETE;

@Data
public class UserDeleteAction extends RequestAction {

    @SerializedName("userId")
    private Long userId;

    public UserDeleteAction() {
        super(USER_DELETE);
    }
}
