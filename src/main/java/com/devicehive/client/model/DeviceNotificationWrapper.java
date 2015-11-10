package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
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
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceNotificationWrapper {\n");
    
    sb.append("    notification: ").append(StringUtil.toIndentedString(notification)).append("\n");
    sb.append("    parameters: ").append(StringUtil.toIndentedString(parameters)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
