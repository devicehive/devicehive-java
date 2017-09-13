import com.devicehive.client.DeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.BasicAuth;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.DeviceService;
import com.devicehive.rest.model.ApiInfo;
import org.junit.Assert;
import org.junit.Test;

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
    }

    @Test
    public void createDevice() {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());
        BasicAuth basicAuth = new BasicAuth("dhadmin", "dhadmin_#911");
        DeviceService deviceService = new DeviceService(basicAuth);
        Assert.assertTrue(deviceService.createDevice());
    }
}
