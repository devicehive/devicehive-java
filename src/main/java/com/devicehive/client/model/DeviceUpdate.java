package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class DeviceUpdate   {
  
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

  @SerializedName("network")
  private Network network = null;

  @SerializedName("deviceClass")
  private DeviceClassUpdate deviceClass = null;

  @SerializedName("blocked")
  private Boolean blocked = false;

  /**
   **/
  @ApiModelProperty(value = "")
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
  @ApiModelProperty(value = "")
  public Network getNetwork() {
    return network;
  }
  public void setNetwork(Network network) {
    this.network = network;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public DeviceClassUpdate getDeviceClass() {
    return deviceClass;
  }
  public void setDeviceClass(DeviceClassUpdate deviceClass) {
    this.deviceClass = deviceClass;
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
    DeviceUpdate deviceUpdate = (DeviceUpdate) o;
    return Objects.equals(guid, deviceUpdate.guid) &&
        Objects.equals(key, deviceUpdate.key) &&
        Objects.equals(name, deviceUpdate.name) &&
        Objects.equals(status, deviceUpdate.status) &&
        Objects.equals(data, deviceUpdate.data) &&
        Objects.equals(network, deviceUpdate.network) &&
        Objects.equals(deviceClass, deviceUpdate.deviceClass) &&
        Objects.equals(blocked, deviceUpdate.blocked);
  }

  @Override
  public int hashCode() {
    return Objects.hash(guid, key, name, status, data, network, deviceClass, blocked);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceUpdate {\n");
    
    sb.append("    guid: ").append(toIndentedString(guid)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    network: ").append(toIndentedString(network)).append("\n");
    sb.append("    deviceClass: ").append(toIndentedString(deviceClass)).append("\n");
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
