import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.api.NetworkApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.*;
import com.devicehive.rest.utils.Const;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Main {


    private static final String LOGIN = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    public static final String URL = "***REMOVED***/";

    ApiClient client = new ApiClient(URL);

    private boolean authenticate() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequestVO requestBody = new JwtRequestVO();
        requestBody.setLogin(LOGIN);
        requestBody.setPassword(PASSWORD);
        Response<JwtTokenVO> response = api.login(requestBody).execute();
        if (response.isSuccessful()) {
            client.addAuthorization(ApiClient.AUTH_API_KEY, ApiKeyAuth.newInstance(response.body().getAccessToken()));
        }
        return response.isSuccessful();
    }

    private boolean registerDevice() throws IOException {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        device.setId(Const.DEVICE_ID);
        DeviceApi deviceApi = client.createService(DeviceApi.class);
        NetworkApi networkApi = client.createService(NetworkApi.class);
        Response<List<Network>> networkResponse = networkApi.list(null, null, null,
                null, null, null).execute();
        List<Network> networks = networkResponse.body();

        if (networks != null && !networks.isEmpty()) {
            device.setNetworkId(networks.get(0).getId());
            Response<Void> response = deviceApi.register(device, Const.DEVICE_ID).execute();
            return response.isSuccessful();
        } else {
            return false;
        }
    }

    @Test
    public void getTokenSuccess() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequestVO requestBody = new JwtRequestVO();
        requestBody.setLogin(LOGIN);
        requestBody.setPassword(PASSWORD);
        Response<JwtTokenVO> response = api.login(requestBody).execute();
        Assert.assertTrue(response.isSuccessful());
        JwtTokenVO tokenVO = response.body();
        Assert.assertTrue(tokenVO != null);
        Assert.assertTrue(tokenVO.getAccessToken() != null);
        Assert.assertTrue(tokenVO.getAccessToken().length() > 0);
    }

    @Test
    public void getTokenFail() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequestVO requestBody = new JwtRequestVO();
        requestBody.setLogin("incorrectLogin");
        requestBody.setPassword("incorrectPassword");
        Response<JwtTokenVO> response = api.login(requestBody).execute();
        Assert.assertTrue(!response.isSuccessful());
        Assert.assertTrue(response.body() == null);
    }


    @Test
    public void registerDeviceSuccess() throws IOException {
        boolean authSuccessFull = authenticate();
        Assert.assertTrue(authSuccessFull);
        boolean registerSuccessFull = registerDevice();
        Assert.assertTrue(registerSuccessFull);
    }


    @Test
    public void getDeviceList() throws IOException {
        boolean authSuccessFull = authenticate();
        Assert.assertTrue(authSuccessFull);
        DeviceApi api = client.createService(DeviceApi.class);
        Response<List<DeviceVO>> response = api.list(null, null, null, null,
                null, null, 0, null).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getDevice() throws IOException {
        boolean authSuccessFull = authenticate();
        Assert.assertTrue(authSuccessFull);
        boolean registerSuccessFull = registerDevice();
        Assert.assertTrue(registerSuccessFull);
        DeviceApi api = client.createService(DeviceApi.class);
        Response<DeviceVO> response = api.get(Const.DEVICE_ID).execute();
        Assert.assertTrue(response.isSuccessful());
    }
}
