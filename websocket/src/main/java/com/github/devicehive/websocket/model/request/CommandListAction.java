/*
 *
 *
 *   CommandListAction.java
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

import com.github.devicehive.rest.model.SortOrder;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import static com.github.devicehive.websocket.model.ActionConstant.COMMAND_LIST;

public class CommandListAction extends RequestAction {

    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("start")
    DateTime start;
    @SerializedName("end")
    DateTime end;
    @SerializedName("command")
    String commandName;
    @SerializedName("status")
    String status;
    @SerializedName("sortOrder")
    SortOrder sortOrder;
    @SerializedName("take")
    Integer take;
    @SerializedName("skip")
    Integer skip;

    public CommandListAction() {
        super(COMMAND_LIST);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(Integer take) {
        this.take = take;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    @Override
    public String toString() {
        return "{\n\"CommandListAction\":{\n"
                + "\"deviceId\":\"" + deviceId + "\""
                + ",\n \"start\":" + start
                + ",\n \"end\":" + end
                + ",\n \"commandName\":\"" + commandName + "\""
                + ",\n \"status\":\"" + status + "\""
                + ",\n \"sortOrder\":\"" + sortOrder + "\""
                + ",\n \"take\":\"" + take + "\""
                + ",\n \"skip\":\"" + skip + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }


}
