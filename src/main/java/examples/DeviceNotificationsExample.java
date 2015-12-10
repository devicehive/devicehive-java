package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.model.DeviceClassUpdate;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.DeviceUpdate;
import com.devicehive.client.model.NotificationPollManyResponse;

import java.util.List;

public class DeviceNotificationsExample {

    public static void main(String[] args) {

        ApiClient apiClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);

        String timestamp = "2015-5-09T14:37:17.404";

        DeviceNotificationApi notificationService = apiClient.createService(DeviceNotificationApi.class);

        List<NotificationPollManyResponse> deviceNotifications = notificationService.pollMany(30L, null, null, timestamp);
        List<DeviceNotification> notifications = notificationService.poll(Const.DEVICE_ID,null,timestamp,30L);
        System.out.println(notifications);
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
