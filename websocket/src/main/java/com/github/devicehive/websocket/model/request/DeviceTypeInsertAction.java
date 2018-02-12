/*
 *
 *
 *   DeviceTypeInsertAction.java
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

import com.github.devicehive.rest.model.DeviceTypeUpdate;
import com.google.gson.annotations.SerializedName;

import static com.github.devicehive.websocket.model.ActionConstant.DEVICE_TYPE_INSERT;

public class DeviceTypeInsertAction extends RequestAction {

    @SerializedName("deviceType")
    private
    DeviceTypeUpdate deviceType;

    public DeviceTypeInsertAction() {
        super(DEVICE_TYPE_INSERT);
    }

    public DeviceTypeUpdate getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeUpdate deviceType) {
        this.deviceType = deviceType;
    }
}
