/*
 *
 *
 *   CommandSubscribeAction.java
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

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

import static com.github.devicehive.websocket.model.ActionConstant.COMMAND_SUBSCRIBE;

public class CommandSubscribeAction extends RequestAction {


    @SerializedName("timestamp")
    DateTime timestamp;
    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("networkIds")
    List<String> networkIds;
    @SerializedName("deviceTypeIds")
    List<String> deviceTypeIds;
    @SerializedName("names")
    List<String> names;
    @SerializedName("returnUpdatedCommands")
    Boolean returnUpdatedCommands;
    @SerializedName("limit")
    Integer limit;


    public CommandSubscribeAction() {
        super(COMMAND_SUBSCRIBE);
    }

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

    public List<String> getNetworkIds() {
        return networkIds;
    }

    public void setNetworkIds(List<String> networkIds) {
        this.networkIds = networkIds;
    }

    public List<String> getDeviceTypeIds() {
        return deviceTypeIds;
    }

    public void setDeviceTypeIds(List<String> deviceTypeIds) {
        this.deviceTypeIds = deviceTypeIds;
    }

    public Boolean getReturnUpdatedCommands() {
        return returnUpdatedCommands;
    }

    public void setReturnUpdatedCommands(Boolean returnUpdatedCommands) {
        this.returnUpdatedCommands = returnUpdatedCommands;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "{\"CommandSubscribeAction\":{"
                + "\"timestamp\":" + timestamp
                + ", \"deviceId\":\"" + deviceId + "\""
                + ", \"networkIds\":" + networkIds
                + ", \"deviceTypeIds\":" + deviceTypeIds
                + ", \"names\":" + names
                + ", \"returnUpdatedCommands\":\"" + returnUpdatedCommands + "\""
                + ", \"limit\":\"" + limit + "\""
                + "}}";
    }
}
