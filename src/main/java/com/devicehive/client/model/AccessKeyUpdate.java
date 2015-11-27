package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;



@ApiModel(description = "")
public class AccessKeyUpdate   {
  
  @SerializedName("label")
  private String label = null;
  
  @SerializedName("expirationDate")
  private String expirationDate = null;
  
  @SerializedName("permissions")
  private List<AccessKeyPermission> permissions = new ArrayList<AccessKeyPermission>();
  
  @SerializedName("type")
  private Integer type = null;
  

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
  public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getExpirationDate() {
    return expirationDate;
  }
  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<AccessKeyPermission> getPermissions() {
    return permissions;
  }
  public void setPermissions(List<AccessKeyPermission> permissions) {
    this.permissions = permissions;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
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
