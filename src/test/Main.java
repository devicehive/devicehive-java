import com.devicehive.client.DeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.rest.model.ApiInfo;
import com.devicehive.rest.model.Configuration;
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

    private static final String URL = "***REMOVED***/";
    private String accessToken = "***REMOVED***";
    private String refreshToken = "***REMOVED***";

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
    public void createToken() throws IOException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());
        deviceHive.login("***REMOVED***", "***REMOVED***");

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        System.out.println(deviceHive.getTokenAuthService().equals(deviceHive.getTokenConfigurationService()));
        System.out.println(deviceHive.getTokenConfigurationService());
        System.out.println(deviceHive.getTokenAuthService());
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void createTokenViaToken() throws IOException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth(refreshToken, accessToken));

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        System.out.println(response.toString());
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void refreshToken() throws IOException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());
        deviceHive.login("***REMOVED***", "***REMOVED***");

        DHResponse<JwtAccessToken> response2 = deviceHive.refreshToken();
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());
    }

    @Test
    public void getConfigurationProperty() throws IOException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth(refreshToken, accessToken));

        DHResponse<Configuration> response = deviceHive.getProperty("jwt.secret");
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void setConfigurationProperty() throws IOException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth(refreshToken, accessToken));

        DHResponse<Configuration> response = deviceHive.setProperty("jwt.secret2", "device2");

        System.out.println(response);

        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void deleteConfigurationProperty() throws IOException {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth(refreshToken, accessToken));

        DHResponse<Configuration> response1 = deviceHive.setProperty("jwt.secret2", "device2");
        System.out.println(response1);
        Assert.assertTrue(response1.isSuccessful());

        DHResponse<Void> response2 = deviceHive.removeProperty("jwt.secret2");
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());
    }
}
