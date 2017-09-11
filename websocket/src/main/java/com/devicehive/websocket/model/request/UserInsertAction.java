package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.request.data.User;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_INSERT;

@Data
public class UserInsertAction extends RequestAction {

    @SerializedName("user")
    private User user;

    public UserInsertAction() {
        super(USER_INSERT);
    }
}
