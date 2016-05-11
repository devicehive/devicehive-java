package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class DeviceClass   {
  
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

  @SerializedName("entityVersion")
  private Long entityVersion = null;

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
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
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
  public Long getEntityVersion() {
    return entityVersion;
  }
  public void setEntityVersion(Long entityVersion) {
    this.entityVersion = entityVersion;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceClass deviceClass = (DeviceClass) o;
    return Objects.equals(id, deviceClass.id) &&
        Objects.equals(name, deviceClass.name) &&
        Objects.equals(version, deviceClass.version) &&
        Objects.equals(offlineTimeout, deviceClass.offlineTimeout) &&
        Objects.equals(data, deviceClass.data) &&
        Objects.equals(entityVersion, deviceClass.entityVersion) &&
        Objects.equals(equipment, deviceClass.equipment) &&
        Objects.equals(permanent, deviceClass.permanent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, version, offlineTimeout, data, entityVersion, equipment, permanent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceClass {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    offlineTimeout: ").append(toIndentedString(offlineTimeout)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
    sb.append("    equipment: ").append(toIndentedString(equipment)).append("\n");
    sb.append("    permanent: ").append(toIndentedString(permanent)).append("\n");
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
