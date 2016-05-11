package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class DeviceCommandWrapper   {
  
  @SerializedName("command")
  private NullableWrapper command = null;

  @SerializedName("parameters")
  private NullableWrapper parameters = null;

  @SerializedName("lifetime")
  private NullableWrapper lifetime = null;

  @SerializedName("status")
  private NullableWrapper status = null;

  @SerializedName("result")
  private NullableWrapper result = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getCommand() {
    return command;
  }
  public void setCommand(NullableWrapper command) {
    this.command = command;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public NullableWrapper getParameters() {
    return parameters;
  }
  public void setParameters(NullableWrapper parameters) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceCommandWrapper deviceCommandWrapper = (DeviceCommandWrapper) o;
    return Objects.equals(command, deviceCommandWrapper.command) &&
        Objects.equals(parameters, deviceCommandWrapper.parameters) &&
        Objects.equals(lifetime, deviceCommandWrapper.lifetime) &&
        Objects.equals(status, deviceCommandWrapper.status) &&
        Objects.equals(result, deviceCommandWrapper.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(command, parameters, lifetime, status, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceCommandWrapper {\n");
    
    sb.append("    command: ").append(toIndentedString(command)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    lifetime: ").append(toIndentedString(lifetime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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
