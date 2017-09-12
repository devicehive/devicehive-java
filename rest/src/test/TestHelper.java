import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.api.NetworkApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.DeviceUpdate;
import com.devicehive.rest.model.JwtRequest;
import com.devicehive.rest.model.JwtToken;
import com.devicehive.rest.model.Network;
import com.devicehive.rest.utils.Const;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;


class TestHelper {

    private static final String LOGIN = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    private static final String URL = "***REMOVED***/";

    ApiClient client = new ApiClient(URL);

    boolean authenticate() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin(LOGIN);
        requestBody.setPassword(PASSWORD);
        Response<JwtToken> response = api.login(requestBody).execute();
        if (response.isSuccessful()) {
            client.addAuthorization(ApiClient.AUTH_API_KEY, ApiKeyAuth.newInstance(response.body().getAccessToken()));
        }
        return response.isSuccessful();
    }


    boolean createDevice(@Nonnull String deviceId) throws IOException {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        device.setId(deviceId);
        DeviceApi deviceApi = client.createService(DeviceApi.class);
        NetworkApi networkApi = client.createService(NetworkApi.class);
        Response<List<Network>> networkResponse = networkApi.list(null, null, null,
                null, null, null).execute();
        List<Network> networks = networkResponse.body();

        if (networks != null && !networks.isEmpty()) {
            device.setNetworkId(networks.get(0).getId());
            Response<Void> response = deviceApi.register(device, deviceId).execute();
            return response.isSuccessful();
        } else {
            return false;
        }
    }

    boolean deleteDevices(String... ids) throws IOException {
        int count = 0;
        DeviceApi api = client.createService(DeviceApi.class);
        for (String id : ids) {
            if (api.delete(id).execute().isSuccessful()) {
                count++;
            } else {
                return false;
            }
        }
        return count == ids.length;
    }
}
