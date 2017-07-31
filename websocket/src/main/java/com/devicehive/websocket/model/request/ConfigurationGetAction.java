package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConfigurationGetAction extends RequestAction {


    public static final String CONFIGURATION_GET = "configuration/get";

    @SerializedName("name")
    private String name;


    public ConfigurationGetAction() {
        super(CONFIGURATION_GET);
    }
}
