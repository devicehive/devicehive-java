package com.devicehive.client.websocket.model;


import com.devicehive.client.json.strategies.JsonPolicyDef;
import com.devicehive.client.model.HiveMessage;
import com.google.common.base.Optional;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents a device command, a unit of information sent to devices. For more details see <a
 * href="http://www.devicehive.com/restful#Reference/DeviceCommand"></a>
 */
public class DeviceCommand implements HiveMessage {

    private static final long serialVersionUID = -5147107009697358635L;

    @JsonPolicyDef({COMMAND_TO_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, POST_COMMAND_TO_DEVICE, COMMAND_LISTED})
    private Long id;

    @JsonPolicyDef({COMMAND_TO_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, POST_COMMAND_TO_DEVICE, COMMAND_LISTED})
    private DateTime timestamp;

    @JsonPolicyDef({COMMAND_TO_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, COMMAND_LISTED})
    private Long userId;

    @JsonPolicyDef({COMMAND_FROM_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, POST_COMMAND_TO_DEVICE, COMMAND_LISTED})
    private String command;

    @JsonPolicyDef({COMMAND_FROM_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, POST_COMMAND_TO_DEVICE, COMMAND_LISTED})
    private String parameters;

    @JsonPolicyDef({COMMAND_FROM_CLIENT, COMMAND_UPDATE_TO_CLIENT, COMMAND_LISTED})
    private Integer lifetime;

    @JsonPolicyDef({COMMAND_FROM_CLIENT, COMMAND_UPDATE_TO_CLIENT, COMMAND_UPDATE_FROM_DEVICE, COMMAND_LISTED})
    private Optional<Integer> flags;

    @JsonPolicyDef({COMMAND_FROM_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, COMMAND_UPDATE_FROM_DEVICE,
        POST_COMMAND_TO_DEVICE, COMMAND_LISTED})
    private Optional<String> status;

    @JsonPolicyDef({COMMAND_FROM_CLIENT, COMMAND_TO_DEVICE, COMMAND_UPDATE_TO_CLIENT, COMMAND_UPDATE_FROM_DEVICE,
        POST_COMMAND_TO_DEVICE, COMMAND_LISTED})
    private Optional<String> result;


    public DeviceCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getTimestamp() {
        return ObjectUtils.cloneIfPossible(timestamp);
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = ObjectUtils.cloneIfPossible(timestamp);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    public Integer getFlags() {
        return NullableWrapper.value(flags);
    }

    public void setFlags(Integer flags) {
        this.flags = Optional.fromNullable(flags);
    }

    public void removeFlags() {
        this.flags = null;
    }

    public String getStatus() {
        return NullableWrapper.value(status);
    }

    public void setStatus(String status) {
        this.status = Optional.fromNullable(status);
    }

    public void removeStatus() {
        this.status = null;
    }

    public String getResult() {
        return NullableWrapper.value(result);
    }

    public void setResult(String result) {
        this.result = Optional.fromNullable(result);
    }

    public void removeResult() {
        this.result = null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceCommand{");
        sb.append("id=").append(id);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", userId=").append(userId);
        sb.append(", command='").append(command).append('\'');
        sb.append(", parameters=").append(parameters);
        sb.append(", lifetime=").append(lifetime);
        sb.append(", flags=").append(flags);
        sb.append(", status=").append(status);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
