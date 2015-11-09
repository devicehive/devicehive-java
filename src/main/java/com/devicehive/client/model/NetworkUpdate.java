package com.devicehive.client.model;

import com.devicehive.client.StringUtil;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
public class NetworkUpdate   {
  
  @SerializedName("id")
  private Long id = null;
  
  @SerializedName("key")
  private NullableWrapper key = null;
  
  @SerializedName("name")
  private NullableWrapper name = null;
  
  @SerializedName("description")
  private NullableWrapper description = null;
  

  
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
  public NullableWrapper getKey() {
    return key;
  }
  public void setKey(NullableWrapper key) {
    this.key = key;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getName() {
    return name;
  }
  public void setName(NullableWrapper name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getDescription() {
    return description;
  }
  public void setDescription(NullableWrapper description) {
    this.description = description;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class NetworkUpdate {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    key: ").append(StringUtil.toIndentedString(key)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    description: ").append(StringUtil.toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
