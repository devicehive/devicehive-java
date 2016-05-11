package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class OAuthClientUpdate   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("name")
  private NullableWrapper name = null;

  @SerializedName("domain")
  private NullableWrapper domain = null;

  @SerializedName("subnet")
  private NullableWrapper subnet = null;

  @SerializedName("redirectUri")
  private NullableWrapper redirectUri = null;

  @SerializedName("oauthId")
  private NullableWrapper oauthId = null;

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
  public NullableWrapper getName() {
    return name;
  }
  public void setName(NullableWrapper name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getDomain() {
    return domain;
  }
  public void setDomain(NullableWrapper domain) {
    this.domain = domain;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getSubnet() {
    return subnet;
  }
  public void setSubnet(NullableWrapper subnet) {
    this.subnet = subnet;
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
  public NullableWrapper getOauthId() {
    return oauthId;
  }
  public void setOauthId(NullableWrapper oauthId) {
    this.oauthId = oauthId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OAuthClientUpdate oAuthClientUpdate = (OAuthClientUpdate) o;
    return Objects.equals(id, oAuthClientUpdate.id) &&
        Objects.equals(name, oAuthClientUpdate.name) &&
        Objects.equals(domain, oAuthClientUpdate.domain) &&
        Objects.equals(subnet, oAuthClientUpdate.subnet) &&
        Objects.equals(redirectUri, oAuthClientUpdate.redirectUri) &&
        Objects.equals(oauthId, oAuthClientUpdate.oauthId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, domain, subnet, redirectUri, oauthId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthClientUpdate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    subnet: ").append(toIndentedString(subnet)).append("\n");
    sb.append("    redirectUri: ").append(toIndentedString(redirectUri)).append("\n");
    sb.append("    oauthId: ").append(toIndentedString(oauthId)).append("\n");
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
