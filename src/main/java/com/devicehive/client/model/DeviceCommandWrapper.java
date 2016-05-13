package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class DeviceCommandWrapper {

    @SerializedName("command")
    private String command = null;

    @SerializedName("parameters")
    private String parameters = null;

    @SerializedName("lifetime")
    private Integer lifetime = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("result")
    private JsonStringWrapper result = null;

    /**
     **/
    @ApiModelProperty(value = "")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public JsonStringWrapper getResult() {
        return result;
    }

    public void setResult(JsonStringWrapper result) {
        this.result = result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceCommandWrapper deviceCommandWrapper = (DeviceCommandWrapper) o;
        return Objects.equals(command, deviceCommandWrapper.command) &&
                Objects.equals(parameters, deviceCommandWrapper.parameters) &&
                Objects.equals(lifetime, deviceCommandWrapper.lifetime) &&
                Objects.equals(status, deviceCommandWrapper.status) &&
                Objects.equals(result, deviceCommandWrapper.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, parameters, lifetime, status, result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceCommandWrapper {\n");

        sb.append("    command: ").append(toIndentedString(command)).append("\n");
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
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
