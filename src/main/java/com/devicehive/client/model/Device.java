package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class Device   {
  
  @SerializedName("id")
  private String id = null;

  @SerializedName("guid")
  private String guid = null;

  @SerializedName("key")
  private String key = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("status")
  private String status = null;

  @SerializedName("data")
  private JsonStringWrapper data = null;

  @SerializedName("deviceClass")
  private DeviceClass deviceClass = null;

  @SerializedName("entityVersion")
  private Long entityVersion = null;

  @SerializedName("blocked")
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
  public String getGuid() {
    return guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
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
  public DeviceClass getDeviceClass() {
    return deviceClass;
  }
  public void setDeviceClass(DeviceClass deviceClass) {
    this.deviceClass = deviceClass;
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
  public Boolean getBlocked() {
    return blocked;
  }
  public void setBlocked(Boolean blocked) {
    this.blocked = blocked;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Device device = (Device) o;
    return Objects.equals(id, device.id) &&
        Objects.equals(guid, device.guid) &&
        Objects.equals(key, device.key) &&
        Objects.equals(name, device.name) &&
        Objects.equals(status, device.status) &&
        Objects.equals(data, device.data) &&
        Objects.equals(deviceClass, device.deviceClass) &&
        Objects.equals(entityVersion, device.entityVersion) &&
        Objects.equals(blocked, device.blocked);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, guid, key, name, status, data, deviceClass, entityVersion, blocked);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Device {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    guid: ").append(toIndentedString(guid)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    deviceClass: ").append(toIndentedString(deviceClass)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
    sb.append("    blocked: ").append(toIndentedString(blocked)).append("\n");
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
