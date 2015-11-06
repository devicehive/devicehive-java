package io.swagger.client.model;

import io.swagger.client.StringUtil;
import io.swagger.client.model.DeviceClass;
import io.swagger.client.model.JsonStringWrapper;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
public class Device   {
  
  @SerializedName("id")
  private Long id = null;
  
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
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Device {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    guid: ").append(StringUtil.toIndentedString(guid)).append("\n");
    sb.append("    key: ").append(StringUtil.toIndentedString(key)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
    sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
    sb.append("    deviceClass: ").append(StringUtil.toIndentedString(deviceClass)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("    blocked: ").append(StringUtil.toIndentedString(blocked)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
