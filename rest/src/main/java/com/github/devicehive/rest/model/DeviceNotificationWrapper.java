/*
 *
 *
 *   DeviceNotificationWrapper.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

/*
 * Device Hive REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 3.3.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.github.devicehive.rest.model;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

/**
 * DeviceNotificationWrapper
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-20T15:03:42.016+03:00")
public class DeviceNotificationWrapper {
  @SerializedName("notification")
  private String notification = null;

  @SerializedName("timestamp")
  private DateTime timestamp = null;

  @SerializedName("parameters")
  private JsonStringWrapper parameters = null;

  public String getNotification() {
    return notification;
  }

  public void setNotification(String notification) {
    this.notification = notification;
  }

  public DateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(DateTime timestamp) {
    this.timestamp = timestamp;
  }

  public JsonStringWrapper getParameters() {
    return parameters;
  }

  public void setParameters(JsonStringWrapper parameters) {
    this.parameters = parameters;
  }

  @Override
  public String toString() {
    return "{\n\"DeviceNotificationWrapper\":{\n"
            + "\"notification\":\"" + notification + "\""
            + ",\n \"timestamp\":" + timestamp
            + ",\n \"parameters\":" + parameters
            + "}\n}";
  }
}

