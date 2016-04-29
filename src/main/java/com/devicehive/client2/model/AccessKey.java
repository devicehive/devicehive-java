package com.devicehive.client2.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;





public class AccessKey   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("label")
  private String label = null;

  @SerializedName("key")
  private String key = null;

  @SerializedName("user")
  private User user = null;

  @SerializedName("expirationDate")
  private Date expirationDate = null;


public enum TypeEnum {
  @SerializedName("DEFAULT")
  DEFAULT("DEFAULT"),

  @SerializedName("SESSION")
  SESSION("SESSION"),

  @SerializedName("OAUTH")
  OAUTH("OAUTH");

  private String value;

  TypeEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

  @SerializedName("type")
  private TypeEnum type = null;

  @SerializedName("permissions")
  private List<AccessKeyPermission> permissions = new ArrayList<AccessKeyPermission>();

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
  public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Date getExpirationDate() {
    return expirationDate;
  }
  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public TypeEnum getType() {
    return type;
  }
  public void setType(TypeEnum type) {
    this.type = type;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public List<AccessKeyPermission> getPermissions() {
    return permissions;
  }
  public void setPermissions(List<AccessKeyPermission> permissions) {
    this.permissions = permissions;
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
    AccessKey accessKey = (AccessKey) o;
    return Objects.equals(id, accessKey.id) &&
        Objects.equals(label, accessKey.label) &&
        Objects.equals(key, accessKey.key) &&
        Objects.equals(user, accessKey.user) &&
        Objects.equals(expirationDate, accessKey.expirationDate) &&
        Objects.equals(type, accessKey.type) &&
        Objects.equals(permissions, accessKey.permissions) &&
        Objects.equals(entityVersion, accessKey.entityVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, key, user, expirationDate, type, permissions, entityVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKey {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
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
