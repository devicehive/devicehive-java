package com.devicehive.rest.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class CommandInsert {
    @SerializedName("id")
    Long commandId;
    DateTime timestamp;
    DateTime lastUpdated;
    Long userId;
}
