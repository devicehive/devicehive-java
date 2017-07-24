package com.devicehive.client.model;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandPollManyResponse  {

    private static final long serialVersionUID = -4390548037685312874L;
    @SerializedName("notification")
    private DeviceCommand command;

    @SerializedName("deviceGuid")
    private String guid;

}
