package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.CONFIGURATION_INSERT;

@Data
public class ConfigurationInsertAction extends RequestAction {


    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;


    public ConfigurationInsertAction() {
        super(CONFIGURATION_INSERT);
    }
}
