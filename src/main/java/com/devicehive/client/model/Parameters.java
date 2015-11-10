package com.devicehive.client.model;


import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class Parameters {

    @SerializedName("id")
    private String id = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("status")
    private String status = null;

    @SerializedName("data")
    private String data = null;

    @SerializedName("network")
    private NetworkUpdate network = null;

    @SerializedName("deviceClass")
    private DeviceClassUpdateNotification deviceClass = null;

    @SerializedName("isBlocked")
    private Boolean isBlocked = null;

    @ApiModelProperty(value = "")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(value = "")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ApiModelProperty(value = "")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @ApiModelProperty(value = "")
    public NetworkUpdate getNetwork() {
        return network;
    }

    public void setNetwork(NetworkUpdate network) {
        this.network = network;
    }

    @ApiModelProperty(value = "")
    public DeviceClassUpdateNotification getDeviceClass() {
        return deviceClass;
    }

    public void setDeviceClass(DeviceClassUpdateNotification deviceClass) {
        this.deviceClass = deviceClass;
    }

    @ApiModelProperty(value = "")
    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Parameters {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
        sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
        sb.append("    network: ").append(StringUtil.toIndentedString(network)).append("\n");
        sb.append("    deviceClass: ").append(StringUtil.toIndentedString(deviceClass)).append("\n");
        sb.append("    isBlocked: ").append(StringUtil.toIndentedString(isBlocked)).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
