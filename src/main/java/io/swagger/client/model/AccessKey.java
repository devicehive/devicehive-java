package io.swagger.client.model;

import io.swagger.client.StringUtil;
import io.swagger.client.model.User;
import java.util.Date;
import java.util.*;
import io.swagger.client.model.AccessKeyPermission;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKey {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    label: ").append(StringUtil.toIndentedString(label)).append("\n");
    sb.append("    key: ").append(StringUtil.toIndentedString(key)).append("\n");
    sb.append("    user: ").append(StringUtil.toIndentedString(user)).append("\n");
    sb.append("    expirationDate: ").append(StringUtil.toIndentedString(expirationDate)).append("\n");
    sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
    sb.append("    permissions: ").append(StringUtil.toIndentedString(permissions)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
