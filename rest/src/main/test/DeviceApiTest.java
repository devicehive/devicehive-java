import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.model.DeviceVO;
import com.devicehive.rest.utils.Const;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class DeviceApiTest extends TestHelper {
    @Test
    public void registerDevice() throws IOException {
        boolean authSuccessful = authenticate();
        Assert.assertTrue(authSuccessful);
        boolean deviceCreated = createDevice();
        Assert.assertTrue(deviceCreated);
    }

    @Test
    public void deleteDevice() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<Void> response = api.delete(Const.FIRST_DEVICE_ID).execute();
        Assert.assertTrue(response.isSuccessful());
    }


    @Test
    public void getDeviceList() throws IOException {
        boolean authSuccessful = authenticate();
        Assert.assertTrue(authSuccessful);
        DeviceApi api = client.createService(DeviceApi.class);
        Response<List<DeviceVO>> response = api.list(null, null, null, null,
                null, null, 0, null).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getDevice() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceApi api = client.createService(DeviceApi.class);
        Response<DeviceVO> response = api.get(Const.FIRST_DEVICE_ID).execute();
        Assert.assertTrue(response.isSuccessful());
    }

}
