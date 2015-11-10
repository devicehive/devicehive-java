package com.devicehive.client.model;


import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class DeviceNotification {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("notification")
    private String notification = null;

    @SerializedName("deviceGuid")
    private String deviceGuid = null;

    @SerializedName("timestamp")
    private String timestamp = null;

    @SerializedName("parameters")
    private Parameters parameters = null;


    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(value = "")
    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @ApiModelProperty(value = "")
    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    @ApiModelProperty(value = "")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @ApiModelProperty(value = "")
    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceNotification {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    notification: ").append(StringUtil.toIndentedString(notification)).append("\n");
        sb.append("    deviceGuid: ").append(StringUtil.toIndentedString(deviceGuid)).append("\n");
        sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
        sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
        sb.append("}");
        return sb.toString();
    }


}
