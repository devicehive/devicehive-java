package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;



@ApiModel(description = "")
public class ApiConfig   {
  
  @SerializedName("providerConfigs")
  private List<IdentityProviderConfig> providerConfigs = new ArrayList<IdentityProviderConfig>();
  
  @SerializedName("sessionTimeout")
  private Long sessionTimeout = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<IdentityProviderConfig> getProviderConfigs() {
    return providerConfigs;
  }
  public void setProviderConfigs(List<IdentityProviderConfig> providerConfigs) {
    this.providerConfigs = providerConfigs;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Long getSessionTimeout() {
    return sessionTimeout;
  }
  public void setSessionTimeout(Long sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiConfig {\n");
    
    sb.append("    providerConfigs: ").append(StringUtil.toIndentedString(providerConfigs)).append("\n");
    sb.append("    sessionTimeout: ").append(StringUtil.toIndentedString(sessionTimeout)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
