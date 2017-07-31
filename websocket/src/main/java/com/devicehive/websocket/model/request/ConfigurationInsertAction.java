package com.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConfigurationInsertAction extends RequestAction {


    public static final String CONFIGURATION_INSERT = "configuration/insert";

    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;


    public ConfigurationInsertAction() {
        super(CONFIGURATION_INSERT);
    }
}
