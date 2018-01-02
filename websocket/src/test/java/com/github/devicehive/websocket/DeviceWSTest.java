/*
 *
 *
 *   DeviceWSTest.java
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

package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static com.github.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceWSTest extends Helper {
    private static final String NETWORK_NAME = "WS Dev1c3 t3zt N37w0k ";
    private static final String DEVICE_NAME = "WS_UNIT_TEST_DEVICE";
    private static final RESTHelper restHelper = new RESTHelper();
    private CountDownLatch latch;
    private DeviceWS deviceWS;
    private Long networkId;
    private String deviceId;

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        deviceWS = client.createDeviceWS();
        networkId = createTestNetwork();
        deviceId = UUID.randomUUID().toString();
    }

    @After
    public void clean() {
        deviceWS.delete(null, deviceId);
    }

    private Long createTestNetwork() throws IOException {
        String networkName = NETWORK_NAME + new Random().nextLong();
        System.out.println("Network name for test: " + networkName);
        NetworkId networkId = restHelper.createNetwork(networkName);
        return networkId.getId();
    }

    @Test
    public void registerDevice() throws IOException, InterruptedException {
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {

            }

            @Override
            public void onGet(Device response) {
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {
                Assert.assertEquals(SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {
            }
        });

        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setName(DEVICE_NAME);
        deviceUpdate.setNetworkId(networkId);
        deviceWS.save(null, deviceId, deviceUpdate);

        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDevice() throws IOException, InterruptedException {
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {
            }

            @Override
            public void onGet(Device response) {
                Assert.assertEquals(deviceId, response.getId());
                Assert.assertEquals(DEVICE_NAME, response.getName());
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {
            }

            @Override
            public void onError(ErrorResponse error) {
            }
        });
        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        deviceWS.get(null, deviceId);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDeviceList() throws IOException, InterruptedException {
        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {
                Assert.assertTrue(response.size() > 0);
                latch.countDown();
            }

            @Override
            public void onGet(Device response) {
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {
            }

            @Override
            public void onError(ErrorResponse error) {
            }
        });
        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        deviceWS.list(null, DEVICE_NAME, null,
                null, null, null, null, 30, 0);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }


    @Test
    public void deleteDevice() throws IOException, InterruptedException {
        deviceWS.setListener(new DeviceListener() {
            @Override
            public void onList(List<Device> response) {

            }

            @Override
            public void onGet(Device response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
                Assert.assertEquals(SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onSave(ResponseAction response) {
            }

            @Override
            public void onError(ErrorResponse error) {
            }

        });
        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        deviceWS.delete(null, deviceId);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }
}
