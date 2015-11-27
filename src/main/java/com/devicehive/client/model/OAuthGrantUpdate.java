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
  private OAuthClient client = null;
  

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
  public OAuthClient getClient() {
    return client;
  }
  public void setClient(OAuthClient client) {
    this.client = client;
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
  @ApiModelProperty(value = "")
  public String getRedirectUri() {
    return redirectUri;
  }
  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
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
