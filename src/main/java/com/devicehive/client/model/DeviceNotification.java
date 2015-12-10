package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.devicehive.client.websocket.model.HiveMessage;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel(description = "")
public class DeviceNotification implements HiveMessage, Comparable<DeviceNotification> {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("notification")
    private String notification = null;

    @SerializedName("deviceGuid")
    private String deviceGuid = null;

    @SerializedName("timestamp")
    private Date timestamp = null;

    @SerializedName("parameters")
    private JsonStringWrapper parameters = null;

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
    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public JsonStringWrapper getParameters() {
        return parameters;
    }

    public void setParameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
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
        sb.append("class DeviceNotification {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    notification: ").append(StringUtil.toIndentedString(notification)).append("\n");
        sb.append("    deviceGuid: ").append(StringUtil.toIndentedString(deviceGuid)).append("\n");
        sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
        sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
        sb.append("    hazelcastKey: ").append(StringUtil.toIndentedString(hazelcastKey)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int compareTo(DeviceNotification notification) {
        return getTimestamp().compareTo(notification.getTimestamp());
    }
}
