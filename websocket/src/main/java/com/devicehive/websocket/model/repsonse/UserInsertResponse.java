package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.UserVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserInsertResponse extends ResponseAction {

    @SerializedName("user")
    UserVO user;
}
