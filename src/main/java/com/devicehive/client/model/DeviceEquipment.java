package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceEquipment {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    code: ").append(StringUtil.toIndentedString(code)).append("\n");
    sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
    sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
    sb.append("    device: ").append(StringUtil.toIndentedString(device)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
