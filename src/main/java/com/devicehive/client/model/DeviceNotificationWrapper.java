package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;


public class DeviceNotificationWrapper   {
  
  @SerializedName("notification")
  private String notification = null;

  @SerializedName("parameters")
  private JsonStringWrapper parameters = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public String getNotification() {
    return notification;
  }
  public void setNotification(String notification) {
    this.notification = notification;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public JsonStringWrapper getParameters() {
    return parameters;
  }
  public void setParameters(JsonStringWrapper parameters) {
    this.parameters = parameters;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceNotificationWrapper deviceNotificationWrapper = (DeviceNotificationWrapper) o;
    return Objects.equals(notification, deviceNotificationWrapper.notification) &&
        Objects.equals(parameters, deviceNotificationWrapper.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(notification, parameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceNotificationWrapper {\n");
    
    sb.append("    notification: ").append(toIndentedString(notification)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
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
