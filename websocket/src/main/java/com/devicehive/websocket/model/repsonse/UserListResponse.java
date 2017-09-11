package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.UserVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class UserListResponse extends ResponseAction {

    @SerializedName("users")
    List<UserVO> users;
}
