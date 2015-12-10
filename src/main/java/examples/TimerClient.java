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
import retrofit.RetrofitError;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerClient {

    private ScheduledExecutorService ses;
    private ApiClient restClient;


    private DeviceNotificationApi notificationApi;
    private DeviceCommandApi commandApi;
    private ApiInfoApi infoApi;
    private DeviceApi deviceApi;

    private String deviceId = null;
    private Date timestamp = null;

    public TimerClient() {
        restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);
        ses = Executors.newScheduledThreadPool(2);
        inflateApi();
    }


    public void inflateApi() {
        notificationApi = restClient.createService(DeviceNotificationApi.class);
        commandApi = restClient.createService(DeviceCommandApi.class);
        infoApi = restClient.createService(ApiInfoApi.class);
        deviceApi = restClient.createService(DeviceApi.class);
    }

    public void run() {

        if (deviceId == null) {
            List<Device> devices = deviceApi.list(Const.NAME, null, null, null, null, null, null, null, null, null, null, null);
            if (devices.size() == 0) {
                System.out.println("No devices was found");
                return;
            } else {
                deviceId = devices.get(0).getGuid();

            }

        }
        timestamp = infoApi.getApiInfo().getServerTimestamp();

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, 10);
                setTimer(Const.DEVICE_ID, true, calendar.getTime());
            }
        }, 0, 5, TimeUnit.SECONDS);


        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pollNotifications(Const.formatTimestamp(timestamp, Const.TIMESTAMP_FORMAT));
            }
        }, 0, 1, TimeUnit.SECONDS);

    }


    private void pollNotifications(String timestamp) {
        List<DeviceNotification> deviceNotifications = notificationApi.poll(Const.DEVICE_ID, null, timestamp, 30L);
        for (DeviceNotification item : deviceNotifications) {
            if (item.getNotification().equals(Const.ALARM)) {
                System.out.println(item);
            }
        }

        if (deviceNotifications.size() != 0) {
            Collections.sort(deviceNotifications);
            System.out.println(deviceNotifications.get(0).getTimestamp().toString());
            System.out.println(deviceNotifications.get(deviceNotifications.size() - 1).getTimestamp());
            updateTimestamp(deviceNotifications.get(deviceNotifications.size() - 1).getTimestamp());
        }

    }

    //Command updating
    private void setTimer(String guid, Boolean isOnTimer, @Nullable Date time) throws RetrofitError {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        if (isOnTimer) {
            deviceCommandWrapper.setCommand(Const.ON);
            JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
            jsonStringWrapper.setJsonString(Const.formatTimestamp(time, Const.ALARM_FORMAT));
            deviceCommandWrapper.setParameters(jsonStringWrapper.getJsonString());
        }
        commandApi.insert(guid, deviceCommandWrapper);
    }


    private void updateTimestamp(Date timestamp) {
        if (this.timestamp == null) {
            this.timestamp = timestamp;
        } else if (this.timestamp.before(timestamp)) {
            this.timestamp = timestamp;
        }
    }
}

