package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;


@ApiModel(description = "")
public class ApiInfo   {
  
  @SerializedName("apiVersion")
  private String apiVersion = null;
  
  @SerializedName("serverTimestamp")
  private DateTime serverTimestamp = null;
  
  @SerializedName("webSocketServerUrl")
  private String webSocketServerUrl = null;
  
  @SerializedName("restServerUrl")
  private String restServerUrl = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getApiVersion() {
    return apiVersion;
  }
  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public DateTime getServerTimestamp() {
    return serverTimestamp;
  }
  public void setServerTimestamp(DateTime serverTimestamp) {
    this.serverTimestamp = serverTimestamp;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getWebSocketServerUrl() {
    return webSocketServerUrl;
  }
  public void setWebSocketServerUrl(String webSocketServerUrl) {
    this.webSocketServerUrl = webSocketServerUrl;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getRestServerUrl() {
    return restServerUrl;
  }
  public void setRestServerUrl(String restServerUrl) {
    this.restServerUrl = restServerUrl;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiInfo {\n");
    
    sb.append("    apiVersion: ").append(StringUtil.toIndentedString(apiVersion)).append("\n");
    sb.append("    serverTimestamp: ").append(StringUtil.toIndentedString(serverTimestamp)).append("\n");
    sb.append("    webSocketServerUrl: ").append(StringUtil.toIndentedString(webSocketServerUrl)).append("\n");
    sb.append("    restServerUrl: ").append(StringUtil.toIndentedString(restServerUrl)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
