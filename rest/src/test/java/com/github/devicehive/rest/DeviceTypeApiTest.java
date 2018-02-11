/*
 *
 *
 *   DeviceTypeApiTest.java
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

import com.github.devicehive.rest.api.DeviceTypeApi;
import com.github.devicehive.rest.model.DeviceType;
import com.github.devicehive.rest.model.DeviceTypeCount;
import com.github.devicehive.rest.model.DeviceTypeListItem;
import com.github.devicehive.rest.model.DeviceTypeUpdate;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import retrofit2.Response;

public class DeviceTypeApiTest extends Helper {

    @Test
    public void insertDeviceType() throws IOException {
        String deviceTypeName = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        long id = createDeviceType(deviceTypeName);
        Assert.assertTrue(authenticated && id != 0);
        Assert.assertTrue(deleteDeviceTypes(id));
    }

    @Test
    public void updateDeviceType() throws IOException {
        String deviceTypeName = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        long id = createDeviceType(deviceTypeName);
        Assert.assertTrue(authenticated && id != 0);
        DeviceTypeApi api = client.createService(DeviceTypeApi.class);
        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setName("UPDATED");
        Response<Void> response = api.update(update, id).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDeviceTypes(id));
    }

    @Test
    public void deleteDeviceType() throws IOException {
        String deviceTypeName = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        long id = createDeviceType(deviceTypeName);
        Assert.assertTrue(authenticated && id != 0);

        DeviceTypeApi api = client.createService(DeviceTypeApi.class);
        Response<Void> response = api.delete(id).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void countDeviceType() throws IOException {
        String deviceTypeName = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        long id = createDeviceType(deviceTypeName);
        Assert.assertTrue(authenticated && id != 0);

        DeviceTypeApi api = client.createService(DeviceTypeApi.class);
        Response<DeviceTypeCount> response = api.count(null, null).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDeviceTypes(id));
    }

    @Test
    public void getDeviceTypeList() throws IOException {
        String deviceTypeName = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        long id = createDeviceType(deviceTypeName);
        Assert.assertTrue(authenticated && id != 0);

        DeviceTypeApi api = client.createService(DeviceTypeApi.class);
        Response<List<DeviceTypeListItem>> response =
                api.list(null, null, null, null,
                        null, null).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDeviceTypes(id));
    }

    @Test
    public void getDevice() throws IOException {
        String deviceTypeName = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        long id = createDeviceType(deviceTypeName);
        Assert.assertTrue(authenticated && id != 0);

        DeviceTypeApi api = client.createService(DeviceTypeApi.class);

        Response<DeviceType> response = api.get(id).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDeviceTypes(id));
    }
}
