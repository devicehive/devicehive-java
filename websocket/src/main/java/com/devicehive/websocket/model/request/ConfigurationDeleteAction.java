package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.CONFIGURATION_DELETE;

@Data
public class ConfigurationDeleteAction extends RequestAction {


    @SerializedName("name")
    private String name;


    public ConfigurationDeleteAction() {
        super(CONFIGURATION_DELETE);
    }
}
