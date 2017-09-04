import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.api.DeviceCommandApi;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.api.NetworkApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.*;
import com.devicehive.rest.utils.Const;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    private static final String LOGIN = "dhadmin";
    private static final String PASSWORD = "dhadmin_#911";
    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    public static final String COMMAND_NAME = "TEST COMMAND";
    public static final String TEST_PROP = "testProp";
    public static final String TEST_VALUE = "testValue";

    private ApiClient client = new ApiClient(URL);

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

    private boolean createDevice() throws IOException {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        device.setId(Const.FIRST_DEVICE_ID);
        DeviceApi deviceApi = client.createService(DeviceApi.class);
        NetworkApi networkApi = client.createService(NetworkApi.class);
        Response<List<Network>> networkResponse = networkApi.list(null, null, null,
                null, null, null).execute();
        List<Network> networks = networkResponse.body();

        if (networks != null && !networks.isEmpty()) {
            device.setNetworkId(networks.get(0).getId());
            Response<Void> response = deviceApi.register(device, Const.FIRST_DEVICE_ID).execute();
            return response.isSuccessful();
        } else {
            return false;
        }
    }

    private boolean createDevice(@Nonnull String deviceId) throws IOException {
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

    private boolean authenticateAndCreateDevice() throws IOException {
        boolean authSuccessful = authenticate();
        boolean deviceCreated = createDevice();
        return authSuccessful && deviceCreated;
    }

    @Test
    public void getToken() throws IOException {
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
    public void getTokenIncorrectCredentials() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequestVO requestBody = new JwtRequestVO();
        requestBody.setLogin("incorrectLogin");
        requestBody.setPassword("incorrectPassword");
        Response<JwtTokenVO> response = api.login(requestBody).execute();
        Assert.assertTrue(!response.isSuccessful());
        Assert.assertTrue(response.body() == null);
    }

    //DeviceApi
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


    //DeviceCommandApi
    private DeviceCommandWrapper getCommandWrapper() {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        deviceCommandWrapper.setCommand(COMMAND_NAME);

        JSONObject data = new JSONObject();
        data.put(TEST_PROP, TEST_VALUE);
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString(new Gson().toJson(data));
        deviceCommandWrapper.setParameters(jsonStringWrapper);
        return deviceCommandWrapper;
    }

    @Test
    public void insertCommand() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceCommandWrapper deviceCommandWrapper = getCommandWrapper();
        DeviceCommandApi commandApi = client.createService(DeviceCommandApi.class);

        Response<DeviceCommand> response = commandApi.insert(Const.FIRST_DEVICE_ID, deviceCommandWrapper).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getCommand() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceCommandWrapper deviceCommandWrapper = getCommandWrapper();

        DeviceCommandApi commandApi = client.createService(DeviceCommandApi.class);

        Response<DeviceCommand> response = commandApi.insert(Const.FIRST_DEVICE_ID, deviceCommandWrapper).execute();

        Assert.assertTrue(response.body() != null);
        DeviceCommand command = response.body();
        assert command != null;
        String commandId = String.valueOf(command.getId());
        Assert.assertTrue(commandId != null);

        Response<DeviceCommand> getResponse = commandApi.get(Const.FIRST_DEVICE_ID, commandId).execute();
        DeviceCommand getCommand = getResponse.body();
        Assert.assertTrue(getResponse.isSuccessful());
        assert getCommand != null;
        Assert.assertTrue(getCommand.getId().equals(command.getId()));
    }


    @Test
    public void pollCommand() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceCommandApi commandApi = client.createService(DeviceCommandApi.class);

        DeviceCommandWrapper deviceCommandWrapper = getCommandWrapper();
        DateTime currentTimestamp = DateTime.now();
        for (int i = 0; i < 5; i++) {
            commandApi.insert(Const.FIRST_DEVICE_ID, deviceCommandWrapper).execute();
        }
        String timestamp = currentTimestamp.toString();
        Response<List<DeviceCommand>> pollResponse = commandApi.poll(Const.FIRST_DEVICE_ID, COMMAND_NAME,
                timestamp, 30L, 10).execute();

        Assert.assertTrue(pollResponse.isSuccessful());
        Assert.assertTrue(pollResponse.body().size() == 5);
    }

    @Test
    public void pollManyCommand() throws IOException {
        boolean authenticated = authenticate();
        boolean firstDeviceCreated = createDevice(Const.FIRST_DEVICE_ID);
        boolean secondDeviceCreated = createDevice(Const.SECOND_DEVICE_ID);
        Assert.assertTrue(authenticated && firstDeviceCreated && secondDeviceCreated);

        DeviceCommandApi commandApi = client.createService(DeviceCommandApi.class);

        DeviceCommandWrapper deviceCommandWrapper = getCommandWrapper();
        DateTime currentTimestamp = DateTime.now();
        for (int i = 0; i < 5; i++) {
            commandApi.insert(Const.FIRST_DEVICE_ID, deviceCommandWrapper).execute();
            commandApi.insert(Const.SECOND_DEVICE_ID, deviceCommandWrapper).execute();
        }
        String timestamp = currentTimestamp.toString();

        List<String> deviceIds = new ArrayList<>();
        deviceIds.add(Const.FIRST_DEVICE_ID);
        deviceIds.add(Const.SECOND_DEVICE_ID);

        Response<List<DeviceCommand>> pollResponse = commandApi.pollMany(
                deviceIds,
                COMMAND_NAME,
                timestamp, 30L, 20).execute();

        Assert.assertTrue(pollResponse.isSuccessful());
        Assert.assertTrue(pollResponse.body().size() == 10);
    }

    @Test
    public void queryCommand() {

    }

    @Test
    public void updateCommand() {

    }

    @Test
    public void waitCommand() {

    }

}
