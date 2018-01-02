/*
 *
 *
 *   NotificationSubscribeAction.java
 *
 *   Copyright (C) 2018 DataArt
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

package com.github.devicehive.websocket.model.request;

import com.github.devicehive.websocket.model.ActionConstant;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

public class NotificationSubscribeAction extends RequestAction {

    @SerializedName("timestamp")
    private DateTime timestamp;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("deviceIds")
    private List<String> deviceIds;
    @SerializedName("names")
    private List<String> names;

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public NotificationSubscribeAction() {
        super(ActionConstant.NOTIFICATION_SUBSCRIBE);
    }
}
