package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.google.gson.annotations.SerializedName;





public class DeviceEquipment   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("code")
  private String code = null;

  @SerializedName("timestamp")
  private Date timestamp = null;

  @SerializedName("parameters")
  private JsonStringWrapper parameters = null;

  @SerializedName("device")
  private Device device = null;

  @SerializedName("entityVersion")
  private Long entityVersion = null;

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
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
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
  @ApiModelProperty(required = true, value = "")
  public Device getDevice() {
    return device;
  }
  public void setDevice(Device device) {
    this.device = device;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceEquipment deviceEquipment = (DeviceEquipment) o;
    return Objects.equals(id, deviceEquipment.id) &&
        Objects.equals(code, deviceEquipment.code) &&
        Objects.equals(timestamp, deviceEquipment.timestamp) &&
        Objects.equals(parameters, deviceEquipment.parameters) &&
        Objects.equals(device, deviceEquipment.device) &&
        Objects.equals(entityVersion, deviceEquipment.entityVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, timestamp, parameters, device, entityVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceEquipment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    device: ").append(toIndentedString(device)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
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
