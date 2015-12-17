package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.model.*;
import org.joda.time.DateTime;
import retrofit.RetrofitError;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerDevice {


    private DateTime alarmTime = null;
    private DateTime currentTime = null;

    private ApiClient restClient;

    private ScheduledExecutorService ses;
    private DeviceNotificationApi notificationApiImpl;
    private DeviceApi deviceApiImpl;
    private ApiInfoApi infoApiImpl;
    private DeviceCommandApi commandApiImpl;


    private DateTime timestamp;

    public TimerDevice() {
        restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);
        ses = Executors.newScheduledThreadPool(3);
        inflateApi();
    }

    public void inflateApi() {
        notificationApiImpl = restClient.createService(DeviceNotificationApi.class);
        commandApiImpl = restClient.createService(DeviceCommandApi.class);
        infoApiImpl = restClient.createService(ApiInfoApi.class);
        deviceApiImpl = restClient.createService(DeviceApi.class);
    }

    public ApiInfo getApiInfo(){
        return infoApiImpl.getApiInfo();
    }

    public void run() {

        registerDevice();

        ApiInfo apiInfo = infoApiImpl.getApiInfo();
        timestamp = apiInfo.getServerTimestamp();

        //Send current timestamp notification
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                DeviceNotificationWrapper myDevice = getTimestampNotificationWrapper();
                notificationApiImpl.insert(Const.DEVICE_ID, myDevice);
            }
        }, 0, 2, TimeUnit.SECONDS);

        //Commands Polling
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<DeviceCommand> commands = commandApiImpl.poll(Const.DEVICE_ID,
                        "ON",
                        timestamp.toString(),
                        30L);
                if (commands.size() != 0) {
                    Collections.sort(commands);
                    DeviceCommand command = commands.get(commands.size() - 1);
                    updateTimestamp(command.getTimestamp());
                    String deviceParams = command.getParameters();
                    alarmTime = DateTime.parse(deviceParams).withMillisOfSecond(0);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        //Send Alarm Notification
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                currentTime = DateTime.now().withMillisOfSecond(0);
                DeviceNotificationWrapper alarmNotification = createAlarmNotificationWrapper();
                if (isAlarmTime()) notificationApiImpl.insert(Const.DEVICE_ID, alarmNotification);
            }
        }, 0, 1, TimeUnit.SECONDS);

    }


    //Adds new device or updates current device
    private void registerDevice() {
        DeviceUpdate device = createDevice();
        deviceApiImpl.register(device, Const.DEVICE_ID);

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

    private boolean isAlarmTime() {
        return alarmTime != null && alarmTime.isEqual(currentTime);
    }

    private DeviceNotificationWrapper getTimestampNotificationWrapper() throws RetrofitError {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        DateTime currentTimestamp = DateTime.now();
        jsonStringWrapper.setJsonString(currentTimestamp.toString());
        wrapper.setParameters(jsonStringWrapper);
        wrapper.setNotification("Timestamp");
        return wrapper;
    }

    private DeviceNotificationWrapper createAlarmNotificationWrapper() {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString("BIP BIP BIP at " + alarmTime);
        wrapper.setParameters(jsonStringWrapper);
        wrapper.setNotification("ALARM");
        return wrapper;
    }

    private void updateTimestamp(DateTime timestamp) {
        if (this.timestamp == null) {
            this.timestamp = timestamp;
        } else if (this.timestamp.isBefore(timestamp)) {
            this.timestamp = timestamp;
        }

    }

}
