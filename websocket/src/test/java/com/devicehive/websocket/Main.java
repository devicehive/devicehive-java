package com.devicehive.websocket;

import com.devicehive.websocket.api.CommandWS;
import com.devicehive.websocket.api.TokenWS;
import com.devicehive.websocket.api.WebSocketClient;
import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.listener.TokenListener;
import com.devicehive.websocket.model.repsonse.CommandInsertResponse;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
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
                System.out.println(response);
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
                    public void onInsert(CommandInsertResponse response) {
                        System.out.println(response);
                    }
                });
                commandWS.subscribe(null, null, "271990123", null, DateTime.now(), 30);
            }
        }).start();

        try {
            latch.await(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
