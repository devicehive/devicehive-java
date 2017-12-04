package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.JwtPayload;
import com.github.devicehive.websocket.api.TokenWS;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.github.devicehive.websocket.model.repsonse.TokenRefreshResponse;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TokenWebSocketTest extends Helper {
    private static String LOGIN = "***REMOVED***";
    private static String PASSWORD = "***REMOVED***";
    private CountDownLatch latch;
    private TokenWS tokenWS;

    public TokenWebSocketTest() {
        this.latch = new CountDownLatch(1);
    }

    @Before
    public void preTest() throws InterruptedException {
        authenticate();
        tokenWS = client.createTokenWS();
    }

    @Test
    public void createToken() throws InterruptedException {
        tokenWS.setListener(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {

            }

            @Override
            public void onCreate(TokenGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        JwtPayload jwtPayload = createAdminJwtPayload(1L);
        tokenWS.create(null, jwtPayload);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    private JwtPayload createAdminJwtPayload(Long userId) {
        JwtPayload jwtPayload = new JwtPayload();
        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);
        jwtPayload.setUserId(userId);
        jwtPayload.setActions(actions);
        jwtPayload.setNetworkIds(networkIds);
        jwtPayload.setDeviceIds(deviceIds);
        jwtPayload.setExpiration(dateTime);
        return jwtPayload;
    }

    @Test
    public void getToken() throws InterruptedException {
        tokenWS.setListener(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
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

            }
        });
        tokenWS.get(null, LOGIN, PASSWORD);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

    @Test
    public void refreshToken() throws InterruptedException {
        tokenWS.setListener(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                String refreshToken = response.getRefreshToken();
                tokenWS.refresh(null, refreshToken);
            }

            @Override
            public void onCreate(TokenGetResponse response) {

            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        tokenWS.get(null, LOGIN, PASSWORD);
        latch.await(awaitTimeout, awaitTimeUnit);
        Assert.assertEquals(latch.getCount(), 0);
    }

}
