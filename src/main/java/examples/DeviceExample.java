package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.model.*;
import com.google.common.base.Strings;
import retrofit.RetrofitError;

import java.net.MalformedURLException;
import java.util.List;

public class DeviceExample {


    public static void main(String[] args) throws MalformedURLException {
        
        ApiClient apiClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.MY_API_KEY);

        String timestamp = "";

        if (Strings.isNullOrEmpty(timestamp)) {

            ApiInfoApi infoService = apiClient.createService(ApiInfoApi.class);
            ApiInfo apiInfo = infoService.getApiInfo();
            timestamp = apiInfo.getServerTimestamp();

            /**FIXME "2015-5-09T14:37:17.404" test data to get list of command
             timestamp = "2015-5-09T14:37:17.404";
             */

            try {
                List<DeviceCommandItem> deviceCommandItems = polling(apiClient, timestamp);
                if (deviceCommandItems.size() > 0) {
                    DeviceCommandItem newest = deviceCommandItems.get(0);
                    timestamp = newest.getTimestamp();
                    udpateCommand(apiClient, newest);
                }
                System.out.println(timestamp);

            } catch (RetrofitError e) {
                e.printStackTrace();
            }
        }
    }


    //Data polling
    private static List<DeviceCommandItem> polling(ApiClient apiClient, String timestamp) throws RetrofitError {
        
        DeviceApi deviceService = apiClient.createService(DeviceApi.class);
        DeviceUpdate device = createDevice();
        deviceService.register(device, Const.ID);
        DeviceCommandApi commandService = apiClient.createService(DeviceCommandApi.class);
        
        return commandService.poll(Const.ID, "", timestamp, 30L);
    }

    //Command updating
    private static void udpateCommand(ApiClient apiClient, DeviceCommandItem newest) {
        
        Long id = newest.getId();
        String guid = newest.getDeviceGuid();
        DeviceCommandWrapper wrapper = getWrapper(newest);
        DeviceCommandApi commandService = apiClient.createService(DeviceCommandApi.class);
        commandService.update(guid, id, wrapper);
    }

    //Command wrapping
    private static DeviceCommandWrapper getWrapper(DeviceCommandItem deviceCommandItem) {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        deviceCommandWrapper.setCommand(deviceCommandItem.getCommand());
        deviceCommandWrapper.setParameters(deviceCommandItem.getParameters());
        return deviceCommandWrapper;
    }

    //Device creating
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
