package com.devicehive.client.model;

import com.devicehive.client.StringUtil;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
public class EquipmentUpdate   {
  
  @SerializedName("id")
  private Long id = null;
  
  @SerializedName("name")
  private NullableWrapper name = null;
  
  @SerializedName("code")
  private NullableWrapper code = null;
  
  @SerializedName("type")
  private NullableWrapper type = null;
  
  @SerializedName("data")
  private NullableWrapper data = null;
  

  
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
  public NullableWrapper getName() {
    return name;
  }
  public void setName(NullableWrapper name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getCode() {
    return code;
  }
  public void setCode(NullableWrapper code) {
    this.code = code;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getType() {
    return type;
  }
  public void setType(NullableWrapper type) {
    this.type = type;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getData() {
    return data;
  }
  public void setData(NullableWrapper data) {
    this.data = data;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class EquipmentUpdate {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    code: ").append(StringUtil.toIndentedString(code)).append("\n");
    sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
    sb.append("    data: ").append(StringUtil.toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
