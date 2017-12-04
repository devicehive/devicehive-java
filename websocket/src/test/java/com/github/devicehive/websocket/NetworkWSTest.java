/*
 *
 *
 *   NetworkWSTest.java
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

import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.rest.model.SortField;
import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.websocket.api.NetworkWS;
import com.github.devicehive.websocket.listener.NetworkListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.NetworkGetResponse;
import com.github.devicehive.websocket.model.repsonse.NetworkInsertResponse;
import com.github.devicehive.websocket.model.repsonse.NetworkListResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NetworkWSTest extends Helper {


    private static final String JAVA_LIB_TEST = "JavaLibTest";
    private CountDownLatch latch;
    private NetworkWS networkWS;
    private long networkId;

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        networkWS = client.createNetworkWS();
        latch = new CountDownLatch(1);
    }

    @After
    public void clear() throws IOException {
        deleteNetwork(networkWS, networkId);
    }

    @Test
    public void A_Insert() throws InterruptedException {
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
                networkId = response.getNetwork().getId();
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
            }
        });
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setName(JAVA_LIB_TEST + new Random().nextLong());
        networkWS.insert(null, networkUpdate);

        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void B_Get() throws InterruptedException {
        networkId = registerNetwork(networkWS, JAVA_LIB_TEST + new Random().nextInt());
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
            }

            @Override
            public void onGet(NetworkGetResponse response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
            }
        });
        networkWS.get(null, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);

    }

    @Test
    public void C_List() throws InterruptedException {
        networkId = registerNetwork(networkWS, JAVA_LIB_TEST + new Random().nextInt());
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                Assert.assertTrue(false);
                latch.countDown();
            }
        });
        networkWS.list(null, null, null, SortField.ID, SortOrder.DESC, 30, 0);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void D_Update() throws InterruptedException {
        networkId = registerNetwork(networkWS, JAVA_LIB_TEST + new Random().nextInt());
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
            }

            @Override
            public void onUpdate(ResponseAction response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
                Assert.assertTrue(false);
            }
        });
        NetworkUpdate networkUpdate = new NetworkUpdate();
        networkUpdate.setDescription("aaaa");
        networkWS.update(null, networkId, networkUpdate);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void E_Delete() throws InterruptedException {
        networkId = registerNetwork(networkWS, JAVA_LIB_TEST + new Random().nextInt());
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
            }

            @Override
            public void onDelete(ResponseAction response) {
                Assert.assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
                Assert.assertTrue(false);
            }
        });
        networkWS.delete(null, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

}
