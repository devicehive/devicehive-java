/*
 *
 *
 *   WsTest.java
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

import com.github.devicehive.websocket.api.CommandWS;
import com.github.devicehive.websocket.api.TokenWS;
import com.github.devicehive.websocket.api.WebSocketClient;
import com.github.devicehive.websocket.listener.CommandListener;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.model.repsonse.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WsTest {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String LOGIN = "dhadmin";
    private static final String PASSWORD = "dhadmin_#911";

    WebSocketClient client = new WebSocketClient
            .Builder()
            .url(URL)
            .token("eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTA3MTMzNDc1ODc0LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.GSv9TeacJlT6GN8F_b9zHQVekr8Tvxmvy_hrO07qxr4")
            .build();

    @Test
    public void getToken() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

       TokenWS tokenWS = client.createTokenWS(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {
                Assert.assertTrue(response.getAccessToken() != null);
                Assert.assertTrue(response.getAccessToken().length() > 0);
                latch.countDown();
            }

            @Override
            public void onCreate(TokenGetResponse response) {

            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                latch.countDown();
            }
        });
        tokenWS.get(null, LOGIN, PASSWORD);
        latch.await(30, TimeUnit.SECONDS);

    }

    @Test
    public void subscribeCommands() {
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CommandWS commandWS = client.createCommandWS(new CommandListener() {
                    @Override
                    public void onInsert(com.github.devicehive.websocket.model.repsonse.CommandInsertResponse response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onUpdate(ResponseAction response) {

                    }

                    @Override
                    public void onList(CommandListResponse response) {

                    }

                    @Override
                    public void onGet(CommandGetResponse response) {

                    }

                    @Override
                    public void onSubscribe(CommandSubscribeResponse response) {

                    }

                    @Override
                    public void onUnsubscribe(ResponseAction response) {

                    }

                    @Override
                    public void onError(ErrorResponse error) {

                    }
                });
                commandWS.subscribe(null, null, "271990123", null, DateTime.now(), 30);
            }
        }).start();

        try {
            latch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
