/*
 *
 *
 *   CommandUnsubscribeAction.java
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

import java.util.List;

import static com.github.devicehive.websocket.model.ActionConstant.COMMAND_UNSUBSCRIBE;

public class CommandUnsubscribeAction extends RequestAction {


    @SerializedName("deviceIds")
    List<String> deviceIds;
    @SerializedName("subscriptionId")
    String subscriptionId;


    public CommandUnsubscribeAction() {
        super(COMMAND_UNSUBSCRIBE);
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Override
    public String toString() {
        return "{\n\"CommandUnsubscribeAction\":{\n"
                + "\"deviceIds\":" + deviceIds
                + ",\n \"subscriptionId\":\"" + subscriptionId + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
