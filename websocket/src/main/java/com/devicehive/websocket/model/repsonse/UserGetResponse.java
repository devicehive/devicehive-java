package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.UserWithNetworkVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserGetResponse extends ResponseAction {

    @SerializedName("user")
    UserWithNetworkVO user;
}
