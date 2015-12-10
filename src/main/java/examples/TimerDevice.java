package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.model.*;
import retrofit.RetrofitError;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerDevice {


    private String alarmTime = null;
    private boolean isAlarmSet = false;

    private ApiClient restClient;

    private ScheduledExecutorService ses;
    private DeviceNotificationApi notificationApi;
    private DeviceApi deviceApi;
    private ApiInfoApi infoApi;
    private DeviceCommandApi commandApi;


    private Date timestamp;

    public TimerDevice() {
        restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);
        ses = Executors.newScheduledThreadPool(2);
        inflateApi();
    }

    public void run() {
        registerDevice();

        timestamp = infoApi.getApiInfo().getServerTimestamp();

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                notificationApi.insert(Const.DEVICE_ID, getTimestampNotificationWrapper());

            }
        }, 0, 2, TimeUnit.SECONDS);


        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlarmSet) {
                    Calendar current = Calendar.getInstance();
                    if (alarmTime.equals(Const.formatTimestamp(current.getTime(), Const.ALARM_FORMAT))) {
                        notificationApi.insert(Const.DEVICE_ID, getAlarmNotificationWrapper());
                        alarmTime = null;
                        isAlarmSet = false;
                    }
                } else {
                    List<DeviceCommand> commands = commandApi.poll(Const.DEVICE_ID,
                            "ON",
                            Const.formatTimestamp(timestamp, Const.TIMESTAMP_FORMAT),
                            30L);
                    Collections.sort(commands);
                    updateTimestamp(commands.get(commands.size() - 1).getTimestamp());
                    if (commands.size() != 0) {
                        DeviceCommand command = commands.get(commands.size() - 1);
                        if (command.getCommand().equals(Const.ON)) {
                            alarmTime = command.getParameters();
                            isAlarmSet = true;
                        }
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);


    }


    public void inflateApi() {
        notificationApi = restClient.createService(DeviceNotificationApi.class);
        commandApi = restClient.createService(DeviceCommandApi.class);
        infoApi = restClient.createService(ApiInfoApi.class);
        deviceApi = restClient.createService(DeviceApi.class);
    }

    private void registerDevice() {
        DeviceUpdate device = createDevice();
        deviceApi.register(device, Const.DEVICE_ID);

    }


    //Command wrapping
    private DeviceNotificationWrapper getTimestampNotificationWrapper() throws RetrofitError {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString(Const.formatTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()), Const.ALARM_FORMAT));
        wrapper.setParameters(jsonStringWrapper);
        wrapper.setNotification("Timestamp");
        return wrapper;
    }

    private DeviceNotificationWrapper getAlarmNotificationWrapper() throws RetrofitError {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString("BIP BIP BIP " + Const.formatTimestamp(timestamp, Const.TIMESTAMP_FORMAT));
        wrapper.setParameters(jsonStringWrapper);
        wrapper.setNotification("ALARM");
        return wrapper;
    }

    private DeviceUpdate createDevice() {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        device.setStatus(Const.STATUS);
        device.setGuid(Const.DEVICE_ID);
        DeviceClassUpdate deviceClass = new DeviceClassUpdate();
        deviceClass.setName(Const.DC_NAME);
        deviceClass.setVersion(Const.DC_VERSION);
        device.setDeviceClass(deviceClass);

        return device;
    }

    private void updateTimestamp(Date timestamp) {
        if (this.timestamp == null) {
            this.timestamp = timestamp;
        } else if (this.timestamp.before(timestamp)) {
            this.timestamp = timestamp;
        }

    }

}
