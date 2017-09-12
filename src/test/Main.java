import com.devicehive.client.DeviceHive;
import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.TokenAuth;
import com.devicehive.client.service.DeviceService;
import com.devicehive.rest.model.ApiInfo;
import org.junit.Assert;
import org.junit.Test;

public class Main {

    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";

    @Test
    public void apiInfoTest() {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());

        DHResponse<ApiInfo> response = deviceHive.getInfo();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void createDevice() {
        DeviceHive deviceHive = new DeviceHive(URL, new TokenAuth());
        DeviceService deviceService = new DeviceService();
        Assert.assertTrue(deviceService.createDevice());
    }
}
