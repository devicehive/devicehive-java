package com.devicehive.client.model;


import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.devicehive.client.StringUtil;

@ApiModel(description = "")
public class DeviceCommandItem {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("command")
    private String command = null;

    @SerializedName("timestamp")
    private String timestamp = null;

    @SerializedName("userId")
    private Integer userId = null;

    @SerializedName("deviceGuid")
    private String deviceGuid = null;

    @SerializedName("parameters")
    private String parameters = null;


    @SerializedName("lifetime")
    private NullableWrapper lifetime = null;

    @SerializedName("status")
    private NullableWrapper status = null;

    @SerializedName("result")
    private NullableWrapper result = null;

    @ApiModelProperty(required = true, value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(value = "")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @ApiModelProperty(required = true, value = "")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @ApiModelProperty(value = "")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @ApiModelProperty(required = true, value = "")
    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

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
    public NullableWrapper getLifetime() {
        return lifetime;
    }
    public void setLifetime(NullableWrapper lifetime) {
        this.lifetime = lifetime;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public NullableWrapper getStatus() {
        return status;
    }
    public void setStatus(NullableWrapper status) {
        this.status = status;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public NullableWrapper getResult() {
        return result;
    }
    public void setResult(NullableWrapper result) {
        this.result = result;
    }
    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceCommandItem {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    command: ").append(StringUtil.toIndentedString(command)).append("\n");
        sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
        sb.append("    userId: ").append(StringUtil.toIndentedString(userId)).append("\n");
        sb.append("    deviceGuid: ").append(StringUtil.toIndentedString(deviceGuid)).append("\n");
        sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
