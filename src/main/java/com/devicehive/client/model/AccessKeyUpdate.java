package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessKeyUpdate accessKeyUpdate = (AccessKeyUpdate) o;
    return Objects.equals(label, accessKeyUpdate.label) &&
        Objects.equals(expirationDate, accessKeyUpdate.expirationDate) &&
        Objects.equals(permissions, accessKeyUpdate.permissions) &&
        Objects.equals(type, accessKeyUpdate.type) &&
        Objects.equals(typeEnum, accessKeyUpdate.typeEnum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, expirationDate, permissions, type, typeEnum);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKeyUpdate {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    typeEnum: ").append(toIndentedString(typeEnum)).append("\n");
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
