package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class Notification {

    @SerializedName("notification")
    private DeviceNotification notification=null;

    @ApiModelProperty(value = "")
    public DeviceNotification getNotification() {
        return notification;
    }

    public void setNotification(DeviceNotification notification) {
        this.notification = notification;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Notification {\n");
        sb.append("    notification: ").append(StringUtil.toIndentedString(notification)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
