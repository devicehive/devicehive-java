/*
 *
 *
 *   CommandTest.java
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

package com.github.devicehive.client;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.TokenAuth;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.rest.model.JsonStringWrapper;
import org.junit.Assert;
import org.junit.Test;

public class CommandTest {
    private static final String DEVICE_ID = "271990123";

    private static final String URL = "http://playground.dev.devicehive.com";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String COM_A = "comA";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTA4NDI0NDQxOTgyLCJ0IjoxfX0.aw55Fzf_hGKoS9-wNFWVrXb0J6cNjdSqwLx5NxkiLZE";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTQ3NDQxOTgyLCJ0IjowfX0.Z421s8zLb85OarAAsEc5koKBHj0DHH-S1YNWetooh7M";
    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));

    private Device device = deviceHive.getDevice(DEVICE_ID);

    @Test
    public void createAndUpdate() throws InterruptedException {
        DHResponse<DeviceCommand> response = device.sendCommand(COM_A, null);
        Assert.assertTrue(response.isSuccessful());
        DeviceCommand command = response.getData();
        command.setResult(new JsonStringWrapper("SUCCESS"));
        command.updateCommand();
        Assert.assertTrue(command.fetchCommandResult().getData() != null);
    }
}
