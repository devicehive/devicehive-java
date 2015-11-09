package com.devicehive.client.model;

import com.devicehive.client.StringUtil;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthClientUpdate {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    domain: ").append(StringUtil.toIndentedString(domain)).append("\n");
    sb.append("    subnet: ").append(StringUtil.toIndentedString(subnet)).append("\n");
    sb.append("    redirectUri: ").append(StringUtil.toIndentedString(redirectUri)).append("\n");
    sb.append("    oauthId: ").append(StringUtil.toIndentedString(oauthId)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
