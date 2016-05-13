package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class DeviceClassUpdate {

    @SerializedName("id")
    private Long id = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("version")
    private String version = null;

    @SerializedName("offlineTimeout")
    private Integer offlineTimeout = null;

    @SerializedName("data")
    private JsonStringWrapper data = null;

    @SerializedName("equipment")
    private List<Equipment> equipment = new ArrayList<Equipment>();

    @SerializedName("permanent")
    private Boolean permanent = false;


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
    public JsonStringWrapper getData() {
        return data;
    }

    public void setData(JsonStringWrapper data) {
        this.data = data;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
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
        sb.append("    equipment: ").append(StringUtil.toIndentedString(equipment)).append("\n");
        sb.append("    permanent: ").append(StringUtil.toIndentedString(permanent)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
