import com.devicehive.client.DeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.BasicAuth;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.DeviceService;
import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.JwtAccessToken;
import com.devicehive.rest.model.JwtToken;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";

    @Test
    public void apiInfoTest() throws InterruptedException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());
        final CountDownLatch latch = new CountDownLatch(1);
        deviceHive.getInfo(new ResponseCallback<ApiInfo>() {
            public void onResponse(DHResponse<ApiInfo> response) {
                System.out.println(response);
                Assert.assertTrue(response.isSuccessful());
                latch.countDown();
            }
        });
        latch.await(20, TimeUnit.SECONDS);
        Assert.assertTrue(deviceHive.getInfo().isSuccessful());
    }

    @Test
    public void createDevice() {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());
        BasicAuth basicAuth = new BasicAuth("dhadmin", "dhadmin_#911");
        DeviceService deviceService = new DeviceService(basicAuth);
        Assert.assertTrue(deviceService.createDevice());
    }

    @Test
    public void createToken() throws IOException {
//        TokenAuth tokenAuth = new TokenAuth("dhadmin", "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTA1MzA5Njg5MzE3LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.RCHGhxOrxduisbgVzQcCSeGaxrO5ojCeAnahv9goBfU");
        BasicAuth basicAuth = new BasicAuth("dhadmin", "dhadmin_#911");
        DeviceHive deviceHive = new DeviceHive(URL, basicAuth);

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void refreshToken() throws IOException {
        BasicAuth basicAuth = new BasicAuth("dhadmin", "dhadmin_#911");
        DeviceHive deviceHive = new DeviceHive(URL, basicAuth);

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        TokenAuth tokenAuth = new TokenAuth(response.getData().getRefreshToken(),
                response.getData().getAccessToken());
        deviceHive = new DeviceHive(URL, tokenAuth);
        DHResponse<JwtAccessToken> response2 = deviceHive.refreshToken();
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());
    }
}
