package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import static com.devicehive.websocket.model.ActionConstant.CONFIGURATION_GET;

@Data
public class ConfigurationGetAction extends RequestAction {


    @SerializedName("name")
    private String name;


    public ConfigurationGetAction() {
        super(CONFIGURATION_GET);
    }
}
