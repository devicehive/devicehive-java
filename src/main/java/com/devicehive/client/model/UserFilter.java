package com.devicehive.client.model;

import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.StatusEnum;
import lombok.Data;

@Data
public class UserFilter {

    String login;
    String loginPattern;
    RoleEnum role;
    StatusEnum status;
    SortField sortField;
    SortOrder sortOrder = SortOrder.DESC;
    Integer take = 30;
    Integer skip = 0;
}
