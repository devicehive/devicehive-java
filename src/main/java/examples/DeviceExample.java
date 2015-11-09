package examples;

import com.devicehive.client.model.*;
import com.google.common.base.Strings;
import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.net.MalformedURLException;
import java.util.List;

public class DeviceExample {

    public static final String URL = "http://playground.devicehive.com/api/rest/";
    private static final String MY_API_KEY = "iD8Ktg1wCnAFpbdEmXvkYjN8e0Ku1sTuEnsOaGDDhxI=";
    private static final String ID = "3d77f31c-bddd-443b-b11c-640946b0581z4123f";
    private static final String NAME = "Graphical Example Device";
    private static final String STATUS = "OFFLINE";
    private static final String DC_NAME = "Graphical Device";
    private static final String DC_VERSION = "1.0";
    private static String TIMESTAMP = "";

    public static void main(String[] args) throws MalformedURLException {
        final ApiClient apiClient = new ApiClient(URL, ApiClient.AUTH_API_KEY, MY_API_KEY);

        if (Strings.isNullOrEmpty(TIMESTAMP)) {
            ApiInfoApi infoService = apiClient.createService(ApiInfoApi.class);
            ApiInfo apiInfo = infoService.getApiInfo();
            TIMESTAMP = apiInfo.getServerTimestamp();

            try {
                polling(apiClient);
            } catch (RetrofitError e) {
                System.out.println(e.toString());
            }
        }
    }

    private static void polling(ApiClient apiClient) throws RetrofitError {
        DeviceUpdate device = createDevice();
        DeviceApi deviceService = apiClient.createService(DeviceApi.class);
        deviceService.register(device, ID);
        DeviceCommandApi commandService = apiClient.createService(DeviceCommandApi.class);

        List<DeviceCommandItem> deviceCommandItems = commandService.poll(ID, "", TIMESTAMP, 30L);
        System.out.println(deviceCommandItems);
        if (deviceCommandItems.size() > 0) {
            TIMESTAMP = deviceCommandItems.get(0).getTimestamp();
            DeviceCommandItem dev = deviceCommandItems.get(0);
            DeviceCommandWrapper deviceCommandWrapper = getWrapper(dev);
            commandService.update(dev.getDeviceGuid(), dev.getId(), deviceCommandWrapper, new Callback<DeviceCommandItem>() {
                @Override
                public void success(DeviceCommandItem deviceCommandItem, Response response) {
                    System.out.println(deviceCommandItem);
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error.toString());
                }
            });
        }
    }

    private static DeviceCommandWrapper getWrapper(DeviceCommandItem deviceCommandItem) {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        deviceCommandWrapper.setCommand(deviceCommandItem.getCommand());
        deviceCommandWrapper.setParameters(deviceCommandItem.getParameters());
        return deviceCommandWrapper;
    }

    private static DeviceUpdate createDevice() {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(NAME);
        device.setStatus(STATUS);
        DeviceClassUpdate deviceClass = new DeviceClassUpdate();
        deviceClass.setName(DC_NAME);
        deviceClass.setVersion(DC_VERSION);
        device.setDeviceClass(deviceClass);

        return device;
    }
}
