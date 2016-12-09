package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

@ApiModel(description = "")
public class DeviceCommand
        implements HiveMessage, Comparable<DeviceCommand> {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("command")
    private String command = null;

    @SerializedName("timestamp")
    private DateTime timestamp = null;

    @SerializedName("userId")
    private Long userId = null;

    @SerializedName("deviceGuid")
    private String deviceGuid = null;

    @SerializedName("parameters")
    private JsonStringWrapper parameters = null;

    @SerializedName("lifetime")
    private Integer lifetime = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("result")
    private JsonStringWrapper result = null;

    @SerializedName("isUpdated")
    private Boolean isUpdated = false;

    @SerializedName("hazelcastKey")
    private String hazelcastKey = null;

    public DeviceCommand id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(example = "null", value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceCommand command(String command) {
        this.command = command;
        return this;
    }

    /**
     * Get command
     *
     * @return command
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public DeviceCommand timestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get timestamp
     *
     * @return timestamp
     **/
    @ApiModelProperty(example = "null", value = "")
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DeviceCommand userId(Long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get userId
     *
     * @return userId
     **/
    @ApiModelProperty(example = "null", value = "")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DeviceCommand deviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
        return this;
    }

    /**
     * Get deviceGuid
     *
     * @return deviceGuid
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    public DeviceCommand parameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get parameters
     *
     * @return parameters
     **/
    @ApiModelProperty(example = "null", value = "")
    public JsonStringWrapper getParameters() {
        return parameters;
    }

    public void setParameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
    }

    public DeviceCommand lifetime(Integer lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    /**
     * Get lifetime
     *
     * @return lifetime
     **/
    @ApiModelProperty(example = "null", value = "")
    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    public DeviceCommand status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeviceCommand result(JsonStringWrapper result) {
        this.result = result;
        return this;
    }

    /**
     * Get result
     *
     * @return result
     **/
    @ApiModelProperty(example = "null", value = "")
    public JsonStringWrapper getResult() {
        return result;
    }

    public void setResult(JsonStringWrapper result) {
        this.result = result;
    }

    public DeviceCommand isUpdated(Boolean isUpdated) {
        this.isUpdated = isUpdated;
        return this;
    }

    /**
     * Get isUpdated
     *
     * @return isUpdated
     **/
    @ApiModelProperty(example = "null", value = "")
    public Boolean getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(Boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public DeviceCommand hazelcastKey(String hazelcastKey) {
        this.hazelcastKey = hazelcastKey;
        return this;
    }

    /**
     * Get hazelcastKey
     *
     * @return hazelcastKey
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getHazelcastKey() {
        return hazelcastKey;
    }

    public void setHazelcastKey(String hazelcastKey) {
        this.hazelcastKey = hazelcastKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceCommand {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    command: ").append(toIndentedString(command)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    deviceGuid: ").append(toIndentedString(deviceGuid)).append("\n");
        sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
        sb.append("    lifetime: ").append(toIndentedString(lifetime)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    result: ").append(toIndentedString(result)).append("\n");
        sb.append("    isUpdated: ").append(toIndentedString(isUpdated)).append("\n");
        sb.append("    hazelcastKey: ").append(toIndentedString(hazelcastKey)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    @Override
    public int compareTo(DeviceCommand deviceCommand) {
        return getTimestamp().compareTo(deviceCommand.getTimestamp());
    }
}
