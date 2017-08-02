package com.devicehive.websocket.model.request;

import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_GET_CURRENT;

@Data
public class UserGetCurrentAction extends RequestAction {

    public UserGetCurrentAction() {
        super(USER_GET_CURRENT);
    }
}
