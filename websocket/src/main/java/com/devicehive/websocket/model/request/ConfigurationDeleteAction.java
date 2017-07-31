package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConfigurationDeleteAction extends RequestAction {


    public static final String CONFIGURATION_DELETE = "configuration/delete";

    @SerializedName("name")
    private String name;


    public ConfigurationDeleteAction() {
        super(CONFIGURATION_DELETE);
    }
}
