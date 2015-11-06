package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.StringUtil;



@ApiModel(description = "")
public class DeviceCommandWrapper   {
  
  @SerializedName("command")
  private String command = null;
  
  @SerializedName("parameters")
  private String parameters = null;
  
  @SerializedName("lifetime")
  private NullableWrapper lifetime = null;
  
  @SerializedName("status")
  private NullableWrapper status = null;
  
  @SerializedName("result")
  private NullableWrapper result = null;
  

  
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
  public NullableWrapper getLifetime() {
    return lifetime;
  }
  public void setLifetime(NullableWrapper lifetime) {
    this.lifetime = lifetime;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getStatus() {
    return status;
  }
  public void setStatus(NullableWrapper status) {
    this.status = status;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getResult() {
    return result;
  }
  public void setResult(NullableWrapper result) {
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
