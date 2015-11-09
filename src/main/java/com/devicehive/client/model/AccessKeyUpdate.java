package com.devicehive.client.model;

import com.devicehive.client.StringUtil;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
public class AccessKeyUpdate   {
  
  @SerializedName("label")
  private NullableWrapper label = null;
  
  @SerializedName("expirationDate")
  private NullableWrapper expirationDate = null;
  
  @SerializedName("permissions")
  private NullableWrapper permissions = null;
  
  @SerializedName("type")
  private NullableWrapper type = null;
  

public enum TypeEnumEnum {
  @SerializedName("DEFAULT")
  DEFAULT("DEFAULT"),

  @SerializedName("SESSION")
  SESSION("SESSION"),

  @SerializedName("OAUTH")
  OAUTH("OAUTH");

  private String value;

  TypeEnumEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

  @SerializedName("typeEnum")
  private TypeEnumEnum typeEnum = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getLabel() {
    return label;
  }
  public void setLabel(NullableWrapper label) {
    this.label = label;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getExpirationDate() {
    return expirationDate;
  }
  public void setExpirationDate(NullableWrapper expirationDate) {
    this.expirationDate = expirationDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getPermissions() {
    return permissions;
  }
  public void setPermissions(NullableWrapper permissions) {
    this.permissions = permissions;
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
  public TypeEnumEnum getTypeEnum() {
    return typeEnum;
  }
  public void setTypeEnum(TypeEnumEnum typeEnum) {
    this.typeEnum = typeEnum;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKeyUpdate {\n");
    
    sb.append("    label: ").append(StringUtil.toIndentedString(label)).append("\n");
    sb.append("    expirationDate: ").append(StringUtil.toIndentedString(expirationDate)).append("\n");
    sb.append("    permissions: ").append(StringUtil.toIndentedString(permissions)).append("\n");
    sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
    sb.append("    typeEnum: ").append(StringUtil.toIndentedString(typeEnum)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
