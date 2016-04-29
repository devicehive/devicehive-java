package com.devicehive.client2.model;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

import com.google.gson.annotations.SerializedName;





public class AccessKeyRequest   {
  
  @SerializedName("providerName")
  private String providerName = null;

  @SerializedName("code")
  private String code = null;

  @SerializedName("redirectUri")
  private String redirectUri = null;

  @SerializedName("accessToken")
  private String accessToken = null;

  @SerializedName("login")
  private String login = null;

  @SerializedName("password")
  private String password = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public String getProviderName() {
    return providerName;
  }
  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
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
  public String getAccessToken() {
    return accessToken;
  }
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessKeyRequest accessKeyRequest = (AccessKeyRequest) o;
    return Objects.equals(providerName, accessKeyRequest.providerName) &&
        Objects.equals(code, accessKeyRequest.code) &&
        Objects.equals(redirectUri, accessKeyRequest.redirectUri) &&
        Objects.equals(accessToken, accessKeyRequest.accessToken) &&
        Objects.equals(login, accessKeyRequest.login) &&
        Objects.equals(password, accessKeyRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(providerName, code, redirectUri, accessToken, login, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessKeyRequest {\n");
    
    sb.append("    providerName: ").append(toIndentedString(providerName)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    redirectUri: ").append(toIndentedString(redirectUri)).append("\n");
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
