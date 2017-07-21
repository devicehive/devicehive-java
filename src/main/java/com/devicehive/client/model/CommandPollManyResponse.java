package com.devicehive.client.model;


import com.devicehive.client.json.strategies.JsonPolicyDef;
import com.devicehive.client.websocket.model.HiveEntity;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.COMMAND_TO_CLIENT;

@Data
@AllArgsConstructor
public class CommandPollManyResponse implements HiveEntity {

    private static final long serialVersionUID = -4390548037685312874L;
    @SerializedName("notification")
    @JsonPolicyDef(COMMAND_TO_CLIENT)
    private DeviceCommand command;

    @SerializedName("deviceGuid")
    @JsonPolicyDef(COMMAND_TO_CLIENT)
    private String guid;

}
