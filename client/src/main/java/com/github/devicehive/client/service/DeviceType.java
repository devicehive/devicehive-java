/*
 *
 *
 *   DeviceType.java
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

package com.github.devicehive.client.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceType {
    private long id;
    private String name;
    private String description;
    private List<Device> devices = new ArrayList<>();

    public boolean updateDeviceType() {
        return DeviceHive.getInstance()
                .getDeviceTypeService()
                .updateDeviceType(this)
                .isSuccessful();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Device> getDevices() {
        return devices;
    }

    static List<DeviceType> list(List<com.github.devicehive.rest.model.DeviceTypeListItem> devices) {
        List<DeviceType> list = new ArrayList<>();
        if (devices == null) {
            return Collections.emptyList();
        }
        for (com.github.devicehive.rest.model.DeviceTypeListItem deviceType :
                devices) {
            list.add(DeviceType.create(deviceType));

        }
        return list;
    }

    static DeviceType create(com.github.devicehive.rest.model.DeviceTypeInserted type, String name, String description) {
        if (type == null) {
            return null;
        }
        DeviceType deviceType = new DeviceType();
        deviceType.id = type.getId();
        deviceType.name = name;
        deviceType.description = description;

        return deviceType;
    }

    static DeviceType create(com.github.devicehive.rest.model.DeviceType type) {
        if (type == null) {
            return null;
        }
        DeviceType deviceType = new DeviceType();
        deviceType.id = type.getId();
        deviceType.name = type.getName();
        deviceType.description = type.getDescription();
        deviceType.devices.clear();
        deviceType.devices.addAll(Device.list(type.getDevices()));
        return deviceType;
    }

    static DeviceType create(com.github.devicehive.rest.model.DeviceTypeListItem type) {
        if (type == null) {
            return null;
        }
        DeviceType deviceType = new DeviceType();
        deviceType.id = type.getId();
        deviceType.name = type.getName();
        deviceType.description = type.getDescription();

        return deviceType;
    }
}
