package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class DeviceCommandWrapper   {
  
  @SerializedName("command")
  private String command = null;
  
  @SerializedName("parameters")
  private String parameters = null;
  
  @SerializedName("lifetime")
  private String lifetime = null;
  
  @SerializedName("status")
  private String status = null;
  
  @SerializedName("result")
  private String result = null;
  

  
  /**
   **/
//  @ApiModelProperty(value = "")
  public String getCommand() {
    return command;
  }
  public void setCommand(String command) {
    this.command = command;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getParameters() {
    return parameters;
  }
  public void setParameters(String parameters) {
    this.parameters = parameters;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getLifetime() {
    return lifetime;
  }
  public void setLifetime(String lifetime) {
    this.lifetime = lifetime;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getResult() {
    return result;
  }
  public void setResult(String result) {
    this.result = result;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceCommandWrapper {\n");
    
    sb.append("    command: ").append(StringUtil.toIndentedString(command)).append("\n");
    sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
    sb.append("    lifetime: ").append(StringUtil.toIndentedString(lifetime)).append("\n");
    sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
    sb.append("    result: ").append(StringUtil.toIndentedString(result)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
