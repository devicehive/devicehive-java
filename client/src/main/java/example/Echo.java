/*
 *
 *
 *   Echo.java
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

package example;

import com.github.devicehive.client.model.CommandFilter;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceCommandsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.TokenAuth;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Echo {
    //DeviceHive settings
    private static final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTA4NDI4MjAxMTA4LCJ0IjoxfX0.bYIkp2Gm_fMHcxcaFm6xqB91Fp8C2DdenmdGgsVNqcc";
    private static final String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTUxMjAxMTA4LCJ0IjowfX0.2wfpmIjrHRtGBoSF3-T77aSAiUYPFSGtgBuGoVZtSxc";

    private static final String URL = "http://playground.dev.devicehive.com/";

    private static final String COMMAND_NAME = "echo_command";

    public static void main(String[] args) {
        final DeviceHive deviceHive = DeviceHive.getInstance().init(URL, new TokenAuth(refreshToken, accessToken));
        final String deviceId = UUID.randomUUID().toString();
        DHResponse<Device> deviceResponse = deviceHive.getDevice(deviceId);
        if (!deviceResponse.isSuccessful()) {
            System.out.println(deviceResponse);
            return;
        }
        Device device = deviceResponse.getData();
        CommandFilter filter = getFilter();
        deviceHive.subscribeCommands(Collections.singletonList(deviceId), filter, new DeviceCommandsCallback() {
            @Override
            public void onSuccess(List<DeviceCommand> commands) {
                System.out.println("echo");
                deviceHive.removeDevice(deviceId);
                Runtime.getRuntime().exit(200);
            }

            @Override
            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        device.sendCommand(COMMAND_NAME, null);
    }

    private static CommandFilter getFilter() {
        CommandFilter filter = new CommandFilter();
        filter.setCommandNames(COMMAND_NAME);
        filter.setStartTimestamp(DateTime.now().minusMinutes(1));
        return filter;
    }
}
