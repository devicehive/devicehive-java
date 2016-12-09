package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

@ApiModel(description = "")
public class DeviceCommandWrapper {
    @SerializedName("command")
    private String command = null;

    @SerializedName("timestamp")
    private DateTime timestamp = null;

    @SerializedName("parameters")
    private JsonStringWrapper parameters = null;

    @SerializedName("lifetime")
    private Integer lifetime = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("result")
    private JsonStringWrapper result = null;

    public DeviceCommandWrapper command(String command) {
        this.command = command;
        return this;
    }

    /**
     * Get command
     * @return command
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public DeviceCommandWrapper timestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get timestamp
     * @return timestamp
     **/
    @ApiModelProperty(example = "null", value = "")
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DeviceCommandWrapper parameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get parameters
     * @return parameters
     **/
    @ApiModelProperty(example = "null", value = "")
    public JsonStringWrapper getParameters() {
        return parameters;
    }

    public void setParameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
    }

    public DeviceCommandWrapper lifetime(Integer lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    /**
     * Get lifetime
     * @return lifetime
     **/
    @ApiModelProperty(example = "null", value = "")
    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    public DeviceCommandWrapper status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     * @return status
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeviceCommandWrapper result(JsonStringWrapper result) {
        this.result = result;
        return this;
    }

    /**
     * Get result
     * @return result
     **/
    @ApiModelProperty(example = "null", value = "")
    public JsonStringWrapper getResult() {
        return result;
    }

    public void setResult(JsonStringWrapper result) {
        this.result = result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceCommandWrapper {\n");

        sb.append("    command: ").append(toIndentedString(command)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
        sb.append("    lifetime: ").append(toIndentedString(lifetime)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

}
