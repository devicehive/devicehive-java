/*
 *
 *
 *   CommandTest.java
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

package com.github.devicehive.client;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.rest.model.JwtToken;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CommandTest extends Helper {

    static final String COM_A = "comA";
    static final String DEVICE_ID = "CommandTest-123";

    private DeviceHive deviceHive;

    private DHResponse<Device> deviceResponse;

    @Before
    public void init() throws IOException {
        String url = System.getProperty("url");
        JwtToken token = login(url);

        deviceHive = DeviceHive.getInstance().init(
                url,
                token.getRefreshToken(),
                token.getAccessToken());
        deviceResponse = deviceHive.getDevice(DEVICE_ID);
    }

    @Test
    public void createAndUpdate() throws InterruptedException {
        Assert.assertTrue(deviceResponse.isSuccessful());
        Device device = deviceResponse.getData();
        DHResponse<DeviceCommand> response = device.sendCommand(COM_A, null);
        Assert.assertTrue(response.isSuccessful());
        DeviceCommand command = response.getData();
        command.setStatus("COMPLETED");
        JsonObject result=new JsonObject();
        result.addProperty("status","SUCCESS");
        command.setResult(result);
        Assert.assertTrue(command.updateCommand());
        Assert.assertTrue(command.fetchCommandResult().getData() != null);
    }
}
