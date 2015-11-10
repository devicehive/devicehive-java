package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class OAuthGrantUpdate   {
  
  @SerializedName("id")
  private Long id = null;
  
  @SerializedName("client")
  private NullableWrapper client = null;
  
  @SerializedName("type")
  private NullableWrapper type = null;
  
  @SerializedName("accessType")
  private NullableWrapper accessType = null;
  
  @SerializedName("redirectUri")
  private NullableWrapper redirectUri = null;
  
  @SerializedName("scope")
  private NullableWrapper scope = null;
  
  @SerializedName("networkIds")
  private NullableWrapper networkIds = null;
  

  
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
  public NullableWrapper getClient() {
    return client;
  }
  public void setClient(NullableWrapper client) {
    this.client = client;
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
  public NullableWrapper getAccessType() {
    return accessType;
  }
  public void setAccessType(NullableWrapper accessType) {
    this.accessType = accessType;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getRedirectUri() {
    return redirectUri;
  }
  public void setRedirectUri(NullableWrapper redirectUri) {
    this.redirectUri = redirectUri;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getScope() {
    return scope;
  }
  public void setScope(NullableWrapper scope) {
    this.scope = scope;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getNetworkIds() {
    return networkIds;
  }
  public void setNetworkIds(NullableWrapper networkIds) {
    this.networkIds = networkIds;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthGrantUpdate {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    client: ").append(StringUtil.toIndentedString(client)).append("\n");
    sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
    sb.append("    accessType: ").append(StringUtil.toIndentedString(accessType)).append("\n");
    sb.append("    redirectUri: ").append(StringUtil.toIndentedString(redirectUri)).append("\n");
    sb.append("    scope: ").append(StringUtil.toIndentedString(scope)).append("\n");
    sb.append("    networkIds: ").append(StringUtil.toIndentedString(networkIds)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
