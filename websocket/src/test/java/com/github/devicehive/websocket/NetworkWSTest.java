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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class NetworkWSTest extends Helper {


    public static final String JAVA_LIB_TEST = "JavaLibTest";

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();

        RESTHelper restHelper = new RESTHelper();
        restHelper.cleanUpNetworks(JAVA_LIB_TEST);
    }

    @Test
    public void list() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        NetworkWS networkWS = client.createNetworkWS();
        networkWS.list(null, null, null, SortField.ID, SortOrder.DESC, 30, 0);
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

        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void get() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        long networkId = registerNetwork(JAVA_LIB_TEST + new Random().nextLong());
        final NetworkWS networkWS = client.createNetworkWS();
        networkWS.get(null, networkId);
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
            }

            @Override
            public void onGet(NetworkGetResponse response) {
                Assert.assertTrue(true);
                deleteNetwork(response.getNetwork().getId());
                latch.countDown();
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

        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);

    }

    @Test
    public void insert() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        final NetworkWS networkWS = client.createNetworkWS();
        NetworkUpdate networkUpdate=new NetworkUpdate();
        networkUpdate.setName(JAVA_LIB_TEST + new Random().nextLong());
        networkWS.insert(null,networkUpdate );
        networkWS.setListener(new NetworkListener() {
            @Override
            public void onList(NetworkListResponse response) {
            }

            @Override
            public void onGet(NetworkGetResponse response) {

            }

            @Override
            public void onInsert(NetworkInsertResponse response) {
                Assert.assertTrue(true);
                deleteNetwork(response.getNetwork().getId());
                latch.countDown();
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
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void update() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        final long networkId = registerNetwork(JAVA_LIB_TEST + new Random().nextLong());
        final NetworkWS networkWS = client.createNetworkWS();
        NetworkUpdate networkUpdate=new NetworkUpdate();
        networkUpdate.setDescription("aaaa");
        networkWS.update(null, networkId,networkUpdate);
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
                Assert.assertTrue(true);
                deleteNetwork(networkId);
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
                Assert.assertTrue(false);
            }
        });
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void delete() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final long networkId = registerNetwork(JAVA_LIB_TEST + new Random().nextLong());
        final NetworkWS networkWS = client.createNetworkWS();
        networkWS.delete(null, networkId);
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
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

}
