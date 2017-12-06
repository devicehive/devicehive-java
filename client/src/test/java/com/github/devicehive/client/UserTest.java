/*
 *
 *
 *   UserTest.java
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
import com.github.devicehive.client.model.NetworkFilter;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.client.service.Network;
import com.github.devicehive.client.service.User;
import com.github.devicehive.rest.model.JsonStringWrapper;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserTest {


    private static final String URL = "http://playground.dev.devicehive.com/api/rest";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String NOTIFICATION_A = "notificationA";
    private static final String NOTIFICATION_B = "notificationB";
    private static final String NOTIFICATION_Z = "notificationZ";
    private static final String COM_A = "comA";
    private static final String COM_B = "comB";
    private static final String COM_Z = "comZ";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTA4NDI4MjAxMTA4LCJ0IjoxfX0.bYIkp2Gm_fMHcxcaFm6xqB91Fp8C2DdenmdGgsVNqcc";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTUxMjAxMTA4LCJ0IjowfX0.2wfpmIjrHRtGBoSF3-T77aSAiUYPFSGtgBuGoVZtSxc";

    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL,refreshToken, accessToken);

    User currentUser = deviceHive.getCurrentUser().getData();

    @Test
    public void getUserNetworks() {
        List<Network> networks = currentUser.getNetworks();
        Assert.assertTrue(networks.size() > 0);
    }

    @Test
    public void updateUser() {
        JsonStringWrapper data = currentUser.getData();
        JsonStringWrapper newData = new JsonStringWrapper();
        newData.setJsonString("BLABLA");
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
