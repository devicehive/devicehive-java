/*
 *
 *
 *   UserTest.java
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
import com.github.devicehive.client.model.NetworkFilter;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.client.service.Network;
import com.github.devicehive.client.service.User;
import com.github.devicehive.rest.model.JwtToken;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UserTest extends Helper {


    private DeviceHive deviceHive;
    private User currentUser;

    @Before
    public void init() throws IOException {
        String url = System.getProperty("url");
        JwtToken token = login(url);

        deviceHive = DeviceHive.getInstance().init(
                url,
                token.getRefreshToken(),
                token.getAccessToken());
        currentUser=deviceHive.getCurrentUser().getData();
    }


    @Test
    public void getUserNetworks() {
        List<Network> networks = currentUser.getNetworks();
        Assert.assertTrue(networks.size() > 0);
    }

    @Test
    public void updateUser() {
        JsonObject data = currentUser.getData();
        JsonObject newData = new JsonObject();
        newData.addProperty("test","test");
        currentUser.setData(newData);
        Assert.assertTrue(currentUser.save());
    }

    @Test
    public void assignNetwork() {
        DHResponse<List<Network>> listDHResponse = deviceHive.listNetworks(new NetworkFilter());
        Assert.assertTrue(listDHResponse.isSuccessful());
        Assert.assertTrue(currentUser.assignNetwork(listDHResponse.getData().get(1).getId()));
        Assert.assertTrue(currentUser.unassignNetwork(listDHResponse.getData().get(1).getId()));
    }
}
