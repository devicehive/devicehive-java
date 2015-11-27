package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class NotificationPollManyResponse   {
  
  @SerializedName("notification")
  private DeviceNotification notification = null;
  
  @SerializedName("guid")
  private String guid = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public DeviceNotification getNotification() {
    return notification;
  }
  public void setNotification(DeviceNotification notification) {
    this.notification = notification;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getGuid() {
    return guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificationPollManyResponse {\n");
    
    sb.append("    notification: ").append(StringUtil.toIndentedString(notification)).append("\n");
    sb.append("    guid: ").append(StringUtil.toIndentedString(guid)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
