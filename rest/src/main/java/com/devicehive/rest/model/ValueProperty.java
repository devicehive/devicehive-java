package com.devicehive.rest.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ValueProperty {

    @SerializedName("value")
    private String value;

    @Override
    public String toString() {
        return "{\n\"ValueProperty\":{\n"
                + "\"value\":\"" + value + "\""
                + "}\n}";
    }
}
