package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.StringUtil;

import java.util.HashSet;
import java.util.Set;



@ApiModel(description = "")
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
  private Set<Equipment> equipment = new HashSet<Equipment>();
  
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
  public Set<Equipment> getEquipment() {
    return equipment;
  }
  public void setEquipment(Set<Equipment> equipment) {
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceClass {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    version: ").append(StringUtil.toIndentedString(version)).append("\n");
    sb.append("    offlineTimeout: ").append(StringUtil.toIndentedString(offlineTimeout)).append("\n");
    sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("    equipment: ").append(StringUtil.toIndentedString(equipment)).append("\n");
    sb.append("    permanent: ").append(StringUtil.toIndentedString(permanent)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
