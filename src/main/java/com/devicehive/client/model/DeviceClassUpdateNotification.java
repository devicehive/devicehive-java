package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "")
public class DeviceClassUpdateNotification {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("version")
    private String version = null;

    @SerializedName("offlineTimeout")
    private Integer offlineTimeout = null;

    @SerializedName("data")
    private String data = null;

    @SerializedName("equipment")
    private List<Equipment> equipments = null;

    @SerializedName("isPermanent")
    private Boolean permanent = null;


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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Integer getOfflineTimeout() {
        return offlineTimeout;
    }

    public void setOfflineTimeout(Integer offlineTimeout) {
        this.offlineTimeout = offlineTimeout;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Boolean getPermanent() {
        return permanent;
    }

    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceClassUpdate {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
        sb.append("    version: ").append(StringUtil.toIndentedString(version)).append("\n");
        sb.append("    offlineTimeout: ").append(StringUtil.toIndentedString(offlineTimeout)).append("\n");
        sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
        sb.append("    equipment: ").append(StringUtil.toIndentedString(equipments)).append("\n");
        sb.append("    permanent: ").append(StringUtil.toIndentedString(permanent)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
