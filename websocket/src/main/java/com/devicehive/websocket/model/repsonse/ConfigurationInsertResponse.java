package com.devicehive.websocket.model.repsonse;

import com.devicehive.websocket.model.repsonse.data.ConfigurationVO;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConfigurationInsertResponse extends ResponseAction {

    @SerializedName("configuration")
    private ConfigurationVO configuration;
}
