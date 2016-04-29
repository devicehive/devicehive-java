package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;





public class OAuthGrant   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("timestamp")
  private Date timestamp = null;

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
  public Date getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Date timestamp) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OAuthGrant oAuthGrant = (OAuthGrant) o;
    return Objects.equals(id, oAuthGrant.id) &&
        Objects.equals(timestamp, oAuthGrant.timestamp) &&
        Objects.equals(authCode, oAuthGrant.authCode) &&
        Objects.equals(client, oAuthGrant.client) &&
        Objects.equals(accessKey, oAuthGrant.accessKey) &&
        Objects.equals(user, oAuthGrant.user) &&
        Objects.equals(type, oAuthGrant.type) &&
        Objects.equals(accessType, oAuthGrant.accessType) &&
        Objects.equals(redirectUri, oAuthGrant.redirectUri) &&
        Objects.equals(scope, oAuthGrant.scope) &&
        Objects.equals(networkIds, oAuthGrant.networkIds) &&
        Objects.equals(entityVersion, oAuthGrant.entityVersion) &&
        Objects.equals(networkIdsAsSet, oAuthGrant.networkIdsAsSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, authCode, client, accessKey, user, type, accessType, redirectUri, scope, networkIds, entityVersion, networkIdsAsSet);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthGrant {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    authCode: ").append(toIndentedString(authCode)).append("\n");
    sb.append("    client: ").append(toIndentedString(client)).append("\n");
    sb.append("    accessKey: ").append(toIndentedString(accessKey)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    accessType: ").append(toIndentedString(accessType)).append("\n");
    sb.append("    redirectUri: ").append(toIndentedString(redirectUri)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    networkIds: ").append(toIndentedString(networkIds)).append("\n");
    sb.append("    entityVersion: ").append(toIndentedString(entityVersion)).append("\n");
    sb.append("    networkIdsAsSet: ").append(toIndentedString(networkIdsAsSet)).append("\n");
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
