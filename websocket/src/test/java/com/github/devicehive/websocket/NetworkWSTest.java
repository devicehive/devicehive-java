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

import com.github.devicehive.rest.model.SortField;
import com.github.devicehive.rest.model.SortOrder;
import com.github.devicehive.websocket.api.NetworkWS;
import com.github.devicehive.websocket.listener.NetworkListener;
import com.github.devicehive.websocket.model.repsonse.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class NetworkWSTest extends Helper {


    @Test
    public void list() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        NetworkWS networkWS = client.createNetworkWS();
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
        authenticate();
        networkWS.list(null, null, null, SortField.ID, SortOrder.DESC, 30, 0);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void get() throws InterruptedException {
        authenticate();
        final CountDownLatch latch = new CountDownLatch(2);
        long networkId = registerNetwork("JavaLibTest" + new Random().nextInt());
        final NetworkWS networkWS = client.createNetworkWS();
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
        networkWS.get(null, networkId);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);

    }

    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

}
