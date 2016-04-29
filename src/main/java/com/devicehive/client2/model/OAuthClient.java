package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import com.google.gson.annotations.SerializedName;





public class OAuthClient   {
  
  @SerializedName("id")
  private Long id = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("domain")
  private String domain = null;

  @SerializedName("subnet")
  private String subnet = null;

  @SerializedName("redirectUri")
  private String redirectUri = null;

  @SerializedName("oauthId")
  private String oauthId = null;

  @SerializedName("oauthSecret")
  private String oauthSecret = null;

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
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getDomain() {
    return domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getSubnet() {
    return subnet;
  }
  public void setSubnet(String subnet) {
    this.subnet = subnet;
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
  public String getOauthId() {
    return oauthId;
  }
  public void setOauthId(String oauthId) {
    this.oauthId = oauthId;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getOauthSecret() {
    return oauthSecret;
  }
  public void setOauthSecret(String oauthSecret) {
    this.oauthSecret = oauthSecret;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OAuthClient oAuthClient = (OAuthClient) o;
    return Objects.equals(id, oAuthClient.id) &&
        Objects.equals(name, oAuthClient.name) &&
        Objects.equals(domain, oAuthClient.domain) &&
        Objects.equals(subnet, oAuthClient.subnet) &&
        Objects.equals(redirectUri, oAuthClient.redirectUri) &&
        Objects.equals(oauthId, oAuthClient.oauthId) &&
        Objects.equals(oauthSecret, oAuthClient.oauthSecret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, domain, subnet, redirectUri, oauthId, oauthSecret);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthClient {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    subnet: ").append(toIndentedString(subnet)).append("\n");
    sb.append("    redirectUri: ").append(toIndentedString(redirectUri)).append("\n");
    sb.append("    oauthId: ").append(toIndentedString(oauthId)).append("\n");
    sb.append("    oauthSecret: ").append(toIndentedString(oauthSecret)).append("\n");
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
