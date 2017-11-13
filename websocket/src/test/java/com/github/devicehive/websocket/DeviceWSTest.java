/*
 *
 *
 *   DeviceTest.java
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

package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.Device;
import com.github.devicehive.rest.model.DeviceUpdate;
import com.github.devicehive.websocket.api.DeviceWS;
import com.github.devicehive.websocket.listener.DeviceListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.github.devicehive.websocket.model.repsonse.ResponseAction.SUCCESS;

public class DeviceWSTest extends Helper {
    public static final String NETWORK_NAME = "Dev1c3 t3zt N37w0k";
    public static final String DEVICE_NAME = "WS_UNIT_TEST_DEVICE";

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();

        RESTHelper restHelper = new RESTHelper();
        restHelper.cleanUpDevices(DEVICE_NAME);
        restHelper.cleanUpNetworks(NETWORK_NAME);
    }

    @Test
    public void registerDevice() throws IOException, InterruptedException {
        final Long networkId = registerNetwork(NETWORK_NAME);
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(1);
        final DeviceWS deviceWS = client.createDeviceWS();
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
                if (error.getCode() == 401) {
                    try {
                        authenticate();
                        Assert.assertNotNull(deviceWS);
                        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Assert.assertTrue(false);
                    }
                    return;
                }
                Assert.assertTrue(false);
            }
        });

        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);

        deleteDevice(deviceId);
        deleteNetwork(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void deleteDevice() throws IOException, InterruptedException {
        final Long networkId = registerNetwork(NETWORK_NAME);
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(1);
        final DeviceWS deviceWS = client.createDeviceWS();
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
                Assert.assertEquals(SUCCESS, response.getStatus());
                deviceWS.delete(null, deviceId);
            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
                if (error.getCode() == 401) {
                    try {
                        authenticate();
                        Assert.assertNotNull(deviceWS);
                        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Assert.assertTrue(false);
                    }
                    return;
                }
                Assert.assertTrue(false);
            }

        });

        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);

        deleteNetwork(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDevice() throws IOException, InterruptedException {
        final Long networkId = registerNetwork(NETWORK_NAME);
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(1);
        final DeviceWS deviceWS = client.createDeviceWS();
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
                Assert.assertEquals(SUCCESS, response.getStatus());
                deviceWS.get(null, deviceId);
            }

            @Override
            public void onError(ErrorResponse error) {
                if (error.getCode() == 401) {
                    try {
                        authenticate();
                        Assert.assertNotNull(deviceWS);
                        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Assert.assertTrue(false);
                    }
                    return;
                }
                Assert.assertTrue(false);
            }
        });
        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);

        deleteDevice(deviceId);
        deleteNetwork(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDeviceList() throws IOException, InterruptedException {
        final Long networkId = registerNetwork(NETWORK_NAME);
        final String deviceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(1);
        final DeviceWS deviceWS = client.createDeviceWS();
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
                Assert.assertEquals(SUCCESS, response.getStatus());
                deviceWS.list(null, DEVICE_NAME, null,
                        null, null, null, null, 30, 0);
            }

            @Override
            public void onError(ErrorResponse error) {
                if (error.getCode() == 401) {
                    try {
                        authenticate();
                        Assert.assertNotNull(deviceWS);
                        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Assert.assertTrue(false);
                    }
                    return;
                }
                Assert.assertTrue(false);
            }
        });
        registerDevice(deviceWS, deviceId, DEVICE_NAME, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);

        deleteDevice(deviceId);
        deleteNetwork(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

}
