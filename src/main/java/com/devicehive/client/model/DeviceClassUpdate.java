package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class DeviceClassUpdate   {
  
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
  private NullableWrapper equipment = null;

  @SerializedName("permanent")
  private NullableWrapper permanent = null;

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
  public NullableWrapper getEquipment() {
    return equipment;
  }
  public void setEquipment(NullableWrapper equipment) {
    this.equipment = equipment;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getPermanent() {
    return permanent;
  }
  public void setPermanent(NullableWrapper permanent) {
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
    DeviceClassUpdate deviceClassUpdate = (DeviceClassUpdate) o;
    return Objects.equals(id, deviceClassUpdate.id) &&
        Objects.equals(name, deviceClassUpdate.name) &&
        Objects.equals(version, deviceClassUpdate.version) &&
        Objects.equals(offlineTimeout, deviceClassUpdate.offlineTimeout) &&
        Objects.equals(data, deviceClassUpdate.data) &&
        Objects.equals(equipment, deviceClassUpdate.equipment) &&
        Objects.equals(permanent, deviceClassUpdate.permanent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, version, offlineTimeout, data, equipment, permanent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceClassUpdate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    offlineTimeout: ").append(toIndentedString(offlineTimeout)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
