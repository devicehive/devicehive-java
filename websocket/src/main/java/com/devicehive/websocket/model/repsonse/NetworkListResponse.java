package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.NetworkVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class NetworkListResponse extends ResponseAction {

    @SerializedName("networks")
    private List<NetworkVO> networks;

}
