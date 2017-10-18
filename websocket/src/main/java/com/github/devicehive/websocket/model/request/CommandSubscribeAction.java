/*
 *
 *
 *   CommandSubscribeAction.java
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

package com.github.devicehive.websocket.model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import java.util.List;

import static com.github.devicehive.websocket.model.ActionConstant.COMMAND_SUBSCRIBE;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommandSubscribeAction extends RequestAction {


    @SerializedName("timestamp")
    DateTime timestamp;
    @SerializedName("deviceId")
    String deviceId;
    @SerializedName("deviceIds")
    List<String> deviceIds;
    @SerializedName("names")
    List<String> names;
    @SerializedName("limit")
    Integer limit;


    public CommandSubscribeAction() {
        super(COMMAND_SUBSCRIBE);
    }

    @Override
    public String toString() {
        return "{\n\"CommandSubscribeAction\":{\n"
                + "\"timestamp\":" + timestamp
                + ",\n \"deviceId\":\"" + deviceId + "\""
                + ",\n \"deviceIds\":" + deviceIds
                + ",\n \"names\":" + names
                + ",\n \"limit\":\"" + limit + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
