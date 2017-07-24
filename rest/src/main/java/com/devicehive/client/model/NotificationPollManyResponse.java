package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import lombok.Data;


@ApiModel(description = "")
@Data
public class NotificationPollManyResponse   {
  
  @SerializedName("notification")
  private DeviceNotification notification = null;
  
  @SerializedName("guid")
  private String guid = null;
  
}
