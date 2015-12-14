package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;


@ApiModel(description = "")
public class DeviceCommand implements HiveMessage, Comparable<DeviceCommand> {

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
    private String parameters = null;

    @SerializedName("lifetime")
    private Integer lifetime = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("result")
    private JsonStringWrapper result = null;

    @SerializedName("isUpStringd")
    private Boolean isUpStringd = false;

    @SerializedName("hazelcastKey")
    private String hazelcastKey = null;


    /**
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
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


    /**
     **/
    @ApiModelProperty(value = "")
    public Boolean getIsUpStringd() {
        return isUpStringd;
    }

    public void setIsUpStringd(Boolean isUpStringd) {
        this.isUpStringd = isUpStringd;
    }


    /**
     **/
    @ApiModelProperty(value = "")
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

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    command: ").append(StringUtil.toIndentedString(command)).append("\n");
        sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
        sb.append("    userId: ").append(StringUtil.toIndentedString(userId)).append("\n");
        sb.append("    deviceGuid: ").append(StringUtil.toIndentedString(deviceGuid)).append("\n");
        sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
        sb.append("    lifetime: ").append(StringUtil.toIndentedString(lifetime)).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
        sb.append("    result: ").append(StringUtil.toIndentedString(result)).append("\n");
        sb.append("    isUpStringd: ").append(StringUtil.toIndentedString(isUpStringd)).append("\n");
        sb.append("    hazelcastKey: ").append(StringUtil.toIndentedString(hazelcastKey)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int compareTo(DeviceCommand deviceCommand) {
        return getTimestamp().compareTo(deviceCommand.getTimestamp());
    }
}
