package com.devicehive.websocket.model.request;

import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.SortOrder;
import com.devicehive.websocket.model.StatusEnum;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.USER_LIST;

@Data
public class UserListAction extends RequestAction {

    @SerializedName("login")
    private String login;
    @SerializedName("loginPattern")
    private String loginPattern;
    @SerializedName("status")
    private StatusEnum status;
    @SerializedName("role")
    private RoleEnum role;
    @SerializedName("sortField")
    private String sortField;
    @SerializedName("sortOrder")
    private SortOrder sortOrder;
    @SerializedName("take")
    private Integer take;
    @SerializedName("skip")
    private Integer skip;

    public UserListAction() {
        super(USER_LIST);
    }
}
