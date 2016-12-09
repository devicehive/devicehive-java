package com.devicehive.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;


public class DeviceNotificationWrapper {
    @SerializedName("notification")
    private String notification = null;

    @SerializedName("timestamp")
    private DateTime timestamp = null;

    @SerializedName("parameters")
    private JsonStringWrapper parameters = null;

    public DeviceNotificationWrapper notification(String notification) {
        this.notification = notification;
        return this;
    }

    /**
     * Get notification
     *
     * @return notification
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public DeviceNotificationWrapper timestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get timestamp
     *
     * @return timestamp
     **/
    @ApiModelProperty(example = "null", value = "")
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DeviceNotificationWrapper parameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get parameters
     *
     * @return parameters
     **/
    @ApiModelProperty(example = "null", value = "")
    public JsonStringWrapper getParameters() {
        return parameters;
    }

    public void setParameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DeviceNotificationWrapper {\n");

        sb.append("    notification: ").append(toIndentedString(notification)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
