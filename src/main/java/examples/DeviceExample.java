package examples;

import com.google.common.base.Strings;
import io.swagger.client.ApiClient;
import io.swagger.client.api.ApiInfoApi;
import io.swagger.client.api.DeviceApi;
import io.swagger.client.api.DeviceCommandApi;
import io.swagger.client.model.*;
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
            infoService.getApiInfo(new Callback<ApiInfoResponse>() {
                @Override
                public void success(ApiInfoResponse apiInfoResponse, Response response) {

                    if (!Strings.isNullOrEmpty(apiInfoResponse.getServerTimestamp())) {
                        TIMESTAMP = apiInfoResponse.getServerTimestamp();
                        System.out.println(TIMESTAMP);
                        polling(apiClient);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } else {


        }
    }

    private static void polling(ApiClient apiClient) {
        DeviceUpdate device = createDevice();
        DeviceApi deviceService = apiClient.createService(DeviceApi.class);
        deviceService.register(device, ID, new Callback<Void>() {

            @Override
            public void success(Void aVoid, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        DeviceCommandApi commandService = apiClient.createService(DeviceCommandApi.class);
        commandService.poll(ID, "", TIMESTAMP, 60L, new Callback<List<DeviceResponse>>() {
            @Override
            public void success(List<DeviceResponse> deviceResponses, Response response) {
                if (deviceResponses.size() > 0) {
                    System.out.println(deviceResponses.toString());
                    if (!Strings.isNullOrEmpty(deviceResponses.get(0).getTimestamp()))
                        TIMESTAMP = deviceResponses.get(0).getTimestamp();
                    deviceResponses.get(0).getDeviceGuid();
                    deviceResponses.get(0).getCommand();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


//        DeviceCommandWrapper wrapper = new DeviceCommandWrapper();
//        wrapper.setCommand("command");
//        wrapper.setParameters("params");
//        commandService.insert(ID, wrapper, new Callback<DeviceCommandResponse>() {
//            @Override
//            public void success(DeviceCommandResponse deviceCommandResponse, Response response) {
//                System.out.println(deviceCommandResponse);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
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
