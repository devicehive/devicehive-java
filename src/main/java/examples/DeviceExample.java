package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.model.DeviceClassUpdate;
import com.devicehive.client.model.DeviceCommandItem;
import com.devicehive.client.model.DeviceCommandWrapper;
import com.devicehive.client.model.DeviceUpdate;
import com.google.common.base.Strings;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.net.MalformedURLException;
import java.util.List;

public class DeviceExample {


    public static void main(String[] args) throws MalformedURLException {
        ApiClient apiClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.MY_API_KEY);
        String timestamp = "";

        if (Strings.isNullOrEmpty(timestamp)) {

            /**FIXME "2015-5-09T14:37:17.404" test data to get list of command
             *ApiInfoApi infoService = apiClient.createService(ApiInfoApi.class);
             *ApiInfo apiInfo = infoService.getApiInfo();
             *TIMESTAMP = apiInfo.getServerTimestamp();
             */

            timestamp = "2015-5-09T14:37:17.404";

            try {
                System.out.println(timestamp);
                timestamp = polling(apiClient, timestamp);
                System.out.println(timestamp);

                DeviceApi deviceService=apiClient.createService(DeviceApi.class);
//                System.out.println("\n"+deviceService.get(Const.ID));
                System.out.println("\n"+deviceService.list(null,null,null,null,null,null,null,null,null,null,20,0));


            } catch (RetrofitError e) {
            e.printStackTrace();
            }
        }
    }

    private static String polling(ApiClient apiClient, String timestamp)  throws RetrofitError{

        DeviceApi deviceService = apiClient.createService(DeviceApi.class);
        DeviceUpdate device = createDevice();
        deviceService.register(device, Const.ID);

        DeviceCommandApi commandService = apiClient.createService(DeviceCommandApi.class);

        List<DeviceCommandItem> deviceCommandItems = commandService.poll(Const.ID, "", timestamp, 30L);
        if (deviceCommandItems.size() > 0) {
            timestamp = deviceCommandItems.get(0).getTimestamp();
            DeviceCommandItem dev = deviceCommandItems.get(0);
            DeviceCommandWrapper deviceCommandWrapper = getWrapper(dev);
            commandService.update(dev.getDeviceGuid(), dev.getId(), deviceCommandWrapper, new Callback<DeviceCommandItem>() {
                @Override
                public void success(DeviceCommandItem deviceCommandItem, Response response) {
                    System.out.println(response.getStatus());

                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error.toString());
                }
            });



        }
        return timestamp;
    }

    private static DeviceCommandWrapper getWrapper(DeviceCommandItem deviceCommandItem) {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        deviceCommandWrapper.setCommand(deviceCommandItem.getCommand());
        deviceCommandWrapper.setParameters(deviceCommandItem.getParameters());
        return deviceCommandWrapper;
    }

    private static DeviceUpdate createDevice() {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        device.setStatus(Const.STATUS);
        DeviceClassUpdate deviceClass = new DeviceClassUpdate();
        deviceClass.setName(Const.DC_NAME);
        deviceClass.setVersion(Const.DC_VERSION);
        device.setDeviceClass(deviceClass);

        return device;
    }
}
