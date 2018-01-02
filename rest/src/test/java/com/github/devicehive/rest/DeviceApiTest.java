/*
 *
 *
 *   DeviceApiTest.java
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

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.DeviceApi;
import com.github.devicehive.rest.model.Device;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DeviceApiTest extends Helper {
    @Test
    public void registerDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);
        Assert.assertTrue(deleteDevices(deviceId));
    }

    @Test
    public void deleteDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<Void> response = api.delete(deviceId).execute();
        Assert.assertTrue(response.isSuccessful());
    }


    @Test
    public void getDeviceList() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<List<Device>> response = api.list(null, null, null, null,
                null, null, 0, null).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<Device> response = api.get(deviceId).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDevices(deviceId));
    }

}
