package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class DeviceUpdate   {
  
  @SerializedName("guid")
  private String guid = null;
  
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceUpdate {\n");
    
    sb.append("    guid: ").append(StringUtil.toIndentedString(guid)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
    sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
    sb.append("    network: ").append(StringUtil.toIndentedString(network)).append("\n");
    sb.append("    deviceClass: ").append(StringUtil.toIndentedString(deviceClass)).append("\n");
    sb.append("    blocked: ").append(StringUtil.toIndentedString(blocked)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
