import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.model.DeviceVO;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DeviceApiTest extends TestHelper {
    @Test
    public void registerDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);
        Assert.assertTrue(deleteDevices(deviceId));
    }

    @Test
    public void deleteDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<Void> response = api.delete(deviceId).execute();
        Assert.assertTrue(response.isSuccessful());
    }


    @Test
    public void getDeviceList() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<List<DeviceVO>> response = api.list(null, null, null, null,
                null, null, 0, null).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getDevice() throws IOException {
        String deviceId = UUID.randomUUID().toString();
        boolean authenticated = authenticate();
        boolean deviceCreated = createDevice(deviceId);
        Assert.assertTrue(authenticated && deviceCreated);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<DeviceVO> response = api.get(deviceId).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteDevices(deviceId));
    }

}
