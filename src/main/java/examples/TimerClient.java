package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.model.Device;
import com.devicehive.client.model.DeviceCommandWrapper;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.JsonStringWrapper;
import com.sun.istack.internal.Nullable;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimerClient {

    private ScheduledExecutorService ses;
    private ApiClient restClient;


    private DeviceNotificationApi notificationApi;
    private DeviceCommandApi commandApi;
    private ApiInfoApi infoApi;
    private DeviceApi deviceApi;

    private String deviceId = null;
    private DateTime timestamp = null;


    public TimerClient() {
        restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);
        ses = Executors.newScheduledThreadPool(2);
        inflateApi();
    }


    private void inflateApi() {
        notificationApi = restClient.createService(DeviceNotificationApi.class);
        commandApi = restClient.createService(DeviceCommandApi.class);
        infoApi = restClient.createService(ApiInfoApi.class);
        deviceApi = restClient.createService(DeviceApi.class);
    }

    void run() {
        if (deviceId == null) {
            try {
                List<Device> devices = deviceApi.list(Const.NAME, null, null, null, null, null,
                        null, null, null, null, null, null).
                        execute().body();

                if (devices.size() == 0) {
                    System.out.println("No devices was found");
                } else {
                    deviceId = devices.get(0).getGuid();
                    try {
                        timestamp = infoApi.getApiInfo().execute()
                                .body()
                                .getServerTimestamp();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    setTimer(Const.DEVICE_ID, true, DateTime.now().plusSeconds(5));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);


        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    pollNotifications(timestamp.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

    }


    private void pollNotifications(String timestamp) throws IOException {
        List<DeviceNotification> deviceNotifications = notificationApi.poll(Const.DEVICE_ID, null, timestamp, 30L)
                .execute()
                .body();
        if (deviceNotifications.size() != 0) {
            Collections.sort(deviceNotifications);
            DeviceNotification notification = deviceNotifications.get(deviceNotifications.size() - 1);
            updateTimestamp(notification.getTimestamp());
        }
        for (DeviceNotification item : deviceNotifications) {
            if (item.getNotification().equals(Const.ALARM)) {
                System.out.println(item);
            }
        }
    }


    //Command updating
    private void setTimer(String guid, Boolean isOnTimer, @Nullable DateTime time) throws IOException {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        if (isOnTimer) {
            deviceCommandWrapper.setCommand(Const.ON);
            JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
            jsonStringWrapper.setJsonString(time.toString());
            deviceCommandWrapper.setParameters(jsonStringWrapper.getJsonString());
        }
        commandApi.insert(guid, deviceCommandWrapper).execute().body();
    }


    private void updateTimestamp(DateTime timestamp) {
        if (this.timestamp == null) {
            this.timestamp = timestamp;
        } else if (this.timestamp.isBefore(timestamp)) {
            this.timestamp = timestamp;
        }
    }

}

