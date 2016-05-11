package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OAuthGrantUpdate oAuthGrantUpdate = (OAuthGrantUpdate) o;
    return Objects.equals(id, oAuthGrantUpdate.id) &&
        Objects.equals(client, oAuthGrantUpdate.client) &&
        Objects.equals(type, oAuthGrantUpdate.type) &&
        Objects.equals(accessType, oAuthGrantUpdate.accessType) &&
        Objects.equals(redirectUri, oAuthGrantUpdate.redirectUri) &&
        Objects.equals(scope, oAuthGrantUpdate.scope) &&
        Objects.equals(networkIds, oAuthGrantUpdate.networkIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, client, type, accessType, redirectUri, scope, networkIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthGrantUpdate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    client: ").append(toIndentedString(client)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    accessType: ").append(toIndentedString(accessType)).append("\n");
    sb.append("    redirectUri: ").append(toIndentedString(redirectUri)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    networkIds: ").append(toIndentedString(networkIds)).append("\n");
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
