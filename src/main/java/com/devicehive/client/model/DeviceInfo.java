package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class DeviceInfo {

    @SerializedName("id")
    private String id = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("data")
    private JsonStringWrapper data = null;

    @SerializedName("network")
    private NetworkUpdate networkUpdate = null;

    @SerializedName("deviceClass")
    private DeviceClassUpdate deviceClassUpdate = null;

    @SerializedName("isBlocked")
    private Boolean blocked = false;


    /**
     **/
    @ApiModelProperty(value = "")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public JsonStringWrapper getData() {
        return data;
    }

    public void setData(JsonStringWrapper data) {
        this.data = data;
    }


    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public DeviceClassUpdate getDeviceClassUpdate() {
        return deviceClassUpdate;
    }

    public void setDeviceClassUpdate(DeviceClassUpdate deviceClassUpdate) {
        this.deviceClassUpdate = deviceClassUpdate;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceInfo {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
        sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
        sb.append("    deviceClassUpdate: ").append(StringUtil.toIndentedString(deviceClassUpdate)).append("\n");
        sb.append("    network: ").append(StringUtil.toIndentedString(networkUpdate)).append("\n");
        sb.append("    blocked: ").append(StringUtil.toIndentedString(blocked)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
