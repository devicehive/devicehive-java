import com.devicehive.rest.api.DeviceCommandApi;
import com.devicehive.rest.model.DeviceCommand;
import com.devicehive.rest.model.DeviceCommandWrapper;
import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.rest.utils.Const;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandApiTest extends TestHelper {

    private static final String COMMAND_NAME = "TEST COMMAND";
    private static final String TEST_PROP = "testProp";
    private static final String TEST_VALUE = "testValue";
    private static final String UPDATED_COMMAND_NAME = "UPDATED COMMAND";

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
    public void queryCommand() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceCommandApi commandApi = client.createService(DeviceCommandApi.class);
        DeviceCommandWrapper deviceCommandWrapper = getCommandWrapper();
        DateTime currentTimestamp = DateTime.now();
        for (int i = 0; i < 5; i++) {
            commandApi.insert(Const.FIRST_DEVICE_ID, deviceCommandWrapper).execute();
        }
        String current = currentTimestamp.toString();
        String endTimestamp = currentTimestamp.plusMinutes(2).toString();
        Response<List<DeviceCommand>> response = commandApi.query(Const.FIRST_DEVICE_ID, current, endTimestamp, COMMAND_NAME,
                null, null, null, 10, 0).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(response.body().size() == 5);
    }

    @Test
    public void updateCommand() throws IOException {
        boolean isSuccessful = authenticateAndCreateDevice();
        Assert.assertTrue(isSuccessful);

        DeviceCommandApi commandApi = client.createService(DeviceCommandApi.class);
        DeviceCommandWrapper wrapper = getCommandWrapper();
        Response<DeviceCommand> response = commandApi.insert(Const.FIRST_DEVICE_ID, wrapper).execute();
        Assert.assertTrue(response.isSuccessful());

        long id = response.body().getId();

        wrapper.setResult(response.body().getResult());
        wrapper.setCommand(UPDATED_COMMAND_NAME);
        wrapper.setLifetime(response.body().getLifetime());
        Response<Void> updatedResponse = commandApi.update(Const.FIRST_DEVICE_ID, id, wrapper).execute();
        Assert.assertTrue(updatedResponse.isSuccessful());
    }

    @Test
    public void waitCommand() {

    }
}
