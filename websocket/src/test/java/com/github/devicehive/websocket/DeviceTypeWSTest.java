/*
 *
 *
 *   DeviceTypeWSTest.java
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

import com.github.devicehive.rest.model.DeviceType;
import com.github.devicehive.rest.model.DeviceTypeCount;
import com.github.devicehive.rest.model.DeviceTypeInserted;
import com.github.devicehive.rest.model.DeviceTypeListItem;
import com.github.devicehive.rest.model.DeviceTypeUpdate;
import com.github.devicehive.websocket.api.DeviceTypeWS;
import com.github.devicehive.websocket.listener.DeviceTypeListener;
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
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceTypeWSTest extends Helper {
    private static final RESTHelper restHelper = new RESTHelper();
    private static final String DEVICE_TYPE_NAME = "WS Dev1c3 Typ3 t3zt ";
    public static final String DESCRIPTION = "Description t3st";

    private CountDownLatch latch;
    private DeviceTypeWS deviceTypeWS;
    private long deviceTypeId = 0;
    private String deviceTypeName;

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        deviceTypeWS = client.createDeviceTypeWS();
        deviceTypeName = DEVICE_TYPE_NAME + new Random().nextInt(300);
    }

    @After
    public void clean() {
        deviceTypeWS.delete(null, deviceTypeId);
    }


    @Test
    public void insertDeviceType() throws IOException, InterruptedException {
        deviceTypeWS.setListener(new DeviceTypeListener() {
            @Override
            public void onError(ErrorResponse error) {
            }

            @Override
            public void onList(List<DeviceTypeListItem> response) {

            }

            @Override
            public void onGet(DeviceType response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onInsert(DeviceTypeInserted response) {
                Assert.assertTrue(true);
                deviceTypeId = response.getId();
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onCount(DeviceTypeCount response) {

            }
        });

        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setDescription(DESCRIPTION);
        deviceTypeWS.insert(null, update);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDeviceType() throws IOException, InterruptedException {
        deviceTypeWS.setListener(new DeviceTypeListener() {
            @Override
            public void onError(ErrorResponse error) {
            }

            @Override
            public void onList(List<DeviceTypeListItem> response) {

            }

            @Override
            public void onGet(DeviceType response) {
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onInsert(DeviceTypeInserted response) {
                Assert.assertTrue(true);
                deviceTypeId = response.getId();
                deviceTypeWS.get(null, deviceTypeId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onCount(DeviceTypeCount response) {

            }
        });

        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setDescription(DESCRIPTION);
        deviceTypeWS.insert(null, update);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void getDeviceTypeList() throws IOException, InterruptedException {
        deviceTypeWS.setListener(new DeviceTypeListener() {
            @Override
            public void onError(ErrorResponse error) {

            }

            @Override
            public void onList(List<DeviceTypeListItem> response) {
                latch.countDown();
            }

            @Override
            public void onGet(DeviceType response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onInsert(DeviceTypeInserted response) {
                Assert.assertTrue(true);
                deviceTypeId = response.getId();
                deviceTypeWS.list(null,
                        null, null,
                        null, null,
                        30, 0);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onCount(DeviceTypeCount response) {

            }
        });

        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setDescription(DESCRIPTION);
        deviceTypeWS.insert(null, update);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }


    @Test
    public void deleteDeviceType() throws IOException, InterruptedException {
        deviceTypeWS.setListener(new DeviceTypeListener() {
            @Override
            public void onError(ErrorResponse error) {

            }

            @Override
            public void onList(List<DeviceTypeListItem> response) {

            }

            @Override
            public void onGet(DeviceType response) {

            }

            @Override
            public void onDelete(ResponseAction response) {
                latch.countDown();
            }

            @Override
            public void onInsert(DeviceTypeInserted response) {
                Assert.assertTrue(true);
                deviceTypeId = response.getId();
                deviceTypeWS.delete(null,
                        deviceTypeId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onCount(DeviceTypeCount response) {

            }
        });

        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setDescription(DESCRIPTION);
        deviceTypeWS.insert(null, update);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }
    @Test
    public void countDeviceType() throws IOException, InterruptedException {
        deviceTypeWS.setListener(new DeviceTypeListener() {
            @Override
            public void onError(ErrorResponse error) {

            }

            @Override
            public void onList(List<DeviceTypeListItem> response) {

            }

            @Override
            public void onGet(DeviceType response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onInsert(DeviceTypeInserted response) {
                Assert.assertTrue(true);
                deviceTypeId = response.getId();
                deviceTypeWS.count(null,
                        null,null);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onCount(DeviceTypeCount response) {
                latch.countDown();
            }
        });

        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setDescription(DESCRIPTION);
        deviceTypeWS.insert(null, update);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }
    @Test
    public void updateDeviceType() throws IOException, InterruptedException {
        deviceTypeWS.setListener(new DeviceTypeListener() {
            @Override
            public void onError(ErrorResponse error) {

            }

            @Override
            public void onList(List<DeviceTypeListItem> response) {

            }

            @Override
            public void onGet(DeviceType response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onInsert(DeviceTypeInserted response) {
                Assert.assertTrue(true);
                deviceTypeId = response.getId();
                DeviceTypeUpdate update = new DeviceTypeUpdate();
                update.setName(deviceTypeName);
                update.setDescription(DESCRIPTION);
                deviceTypeWS.update(null,
                        deviceTypeId,update);
            }

            @Override
            public void onUpdate(ResponseAction response) {
                latch.countDown();
            }

            @Override
            public void onCount(DeviceTypeCount response) {

            }
        });

        DeviceTypeUpdate update = new DeviceTypeUpdate();
        update.setName(deviceTypeName);
        update.setDescription(DESCRIPTION);
        deviceTypeWS.insert(null, update);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(0, latch.getCount());
    }
}
