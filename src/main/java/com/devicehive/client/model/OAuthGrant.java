package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;



@ApiModel(description = "")
public class OAuthGrant   {
  
  @SerializedName("id")
  private Long id = null;
  
  @SerializedName("timestamp")
  private String timestamp = null;
  
  @SerializedName("authCode")
  private String authCode = null;
  
  @SerializedName("client")
  private OAuthClient client = null;
  
  @SerializedName("accessKey")
  private AccessKey accessKey = null;
  
  @SerializedName("user")
  private User user = null;
  

public enum TypeEnum {
  @SerializedName("CODE")
  CODE("CODE"),

  @SerializedName("TOKEN")
  TOKEN("TOKEN"),

  @SerializedName("PASSWORD")
  PASSWORD("PASSWORD");

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
  

public enum AccessTypeEnum {
  @SerializedName("ONLINE")
  ONLINE("ONLINE"),

  @SerializedName("OFFLINE")
  OFFLINE("OFFLINE");

  private String value;

  AccessTypeEnum(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}

  @SerializedName("accessType")
  private AccessTypeEnum accessType = null;
  
  @SerializedName("redirectUri")
  private String redirectUri = null;
  
  @SerializedName("scope")
  private String scope = null;
  
  @SerializedName("networkIds")
  private JsonStringWrapper networkIds = null;
  
  @SerializedName("entityVersion")
  private Long entityVersion = null;
  
  @SerializedName("networkIdsAsSet")
  private List<Long> networkIdsAsSet = new ArrayList<Long>();
  

  
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
  public String getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getAuthCode() {
    return authCode;
  }
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public OAuthClient getClient() {
    return client;
  }
  public void setClient(OAuthClient client) {
    this.client = client;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public AccessKey getAccessKey() {
    return accessKey;
  }
  public void setAccessKey(AccessKey accessKey) {
    this.accessKey = accessKey;
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
  public TypeEnum getType() {
    return type;
  }
  public void setType(TypeEnum type) {
    this.type = type;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public AccessTypeEnum getAccessType() {
    return accessType;
  }
  public void setAccessType(AccessTypeEnum accessType) {
    this.accessType = accessType;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getRedirectUri() {
    return redirectUri;
  }
  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getScope() {
    return scope;
  }
  public void setScope(String scope) {
    this.scope = scope;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getNetworkIds() {
    return networkIds;
  }
  public void setNetworkIds(JsonStringWrapper networkIds) {
    this.networkIds = networkIds;
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
  public List<Long> getNetworkIdsAsSet() {
    return networkIdsAsSet;
  }
  public void setNetworkIdsAsSet(List<Long> networkIdsAsSet) {
    this.networkIdsAsSet = networkIdsAsSet;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthGrant {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    timestamp: ").append(StringUtil.toIndentedString(timestamp)).append("\n");
    sb.append("    authCode: ").append(StringUtil.toIndentedString(authCode)).append("\n");
    sb.append("    client: ").append(StringUtil.toIndentedString(client)).append("\n");
    sb.append("    accessKey: ").append(StringUtil.toIndentedString(accessKey)).append("\n");
    sb.append("    user: ").append(StringUtil.toIndentedString(user)).append("\n");
    sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
    sb.append("    accessType: ").append(StringUtil.toIndentedString(accessType)).append("\n");
    sb.append("    redirectUri: ").append(StringUtil.toIndentedString(redirectUri)).append("\n");
    sb.append("    scope: ").append(StringUtil.toIndentedString(scope)).append("\n");
    sb.append("    networkIds: ").append(StringUtil.toIndentedString(networkIds)).append("\n");
    sb.append("    entityVersion: ").append(StringUtil.toIndentedString(entityVersion)).append("\n");
    sb.append("    networkIdsAsSet: ").append(StringUtil.toIndentedString(networkIdsAsSet)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
