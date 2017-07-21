package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.*;
import com.devicehive.client.model.*;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimerDevice {

    private DateTime alarmTime = null;
    private DateTime currentTime = null;

    private ApiClient restClient;

    private ScheduledExecutorService ses;
    private DeviceNotificationApi notificationApiImpl;
    private DeviceApi deviceApiImpl;
    private ApiInfoVOApi infoApiImpl;
    private DeviceCommandApi commandApiImpl;
    private NetworkApi networkApiImpl;

    private DateTime timestamp;

    public TimerDevice() {
        restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.API_KEY);
        ses = Executors.newScheduledThreadPool(3);
        inflateApi();
    }

    private void inflateApi() {
        notificationApiImpl = restClient.createService(DeviceNotificationApi.class);
        commandApiImpl = restClient.createService(DeviceCommandApi.class);
        infoApiImpl = restClient.createService(ApiInfoVOApi.class);
        deviceApiImpl = restClient.createService(DeviceApi.class);
        networkApiImpl = restClient.createService(NetworkApi.class);
    }

    void run() throws IOException {

        registerDevice();

        ApiInfoVO apiInfo = infoApiImpl.getApiInfo().execute().body();

        timestamp = apiInfo.getServerTimestamp();
        //Send current timestamp notification
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                DeviceNotificationWrapper myDevice = getTimestampNotificationWrapper();
                try {
                    notificationApiImpl.insert(Const.DEVICE_ID, myDevice).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);

        //Commands Polling
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<DeviceCommand> commands = null;
                try {
                    commandApiImpl.poll(Const.DEVICE_ID, "ON", timestamp.toString(), 30L, null)
                            .enqueue(new Callback<List<DeviceCommand>>() {
                                @Override
                                public void onResponse(Call<List<DeviceCommand>> call, Response<List<DeviceCommand>> response) {

                                }

                                @Override
                                public void onFailure(Call<List<DeviceCommand>> call, Throwable throwable) {
                                    System.out.println(throwable.getMessage());
                                }
                            });
                    commands = commandApiImpl.poll(Const.DEVICE_ID, "ON", timestamp.toString(), 30L, null)
                            .execute()
                            .body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (commands != null && commands.size() != 0) {
                    Collections.sort(commands);
                    DeviceCommand command = commands.get(commands.size() - 1);
                    updateTimestamp(command.getTimestamp());
                    JsonStringWrapper deviceParams = command.getParameters();
                    alarmTime = DateTime.parse(deviceParams.getJsonString()).withMillisOfSecond(0);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        //Send Alarm Notification
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                currentTime = DateTime.now().withMillisOfSecond(0);
                DeviceNotificationWrapper alarmNotification = createAlarmNotificationWrapper();
                if (isAlarmTime()) {
                    try {
                        notificationApiImpl.insert(Const.DEVICE_ID, alarmNotification).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    //Adds new device or updates current device

    private void registerDevice() {
        DeviceUpdate device = createDevice();
        try {
            List<NetworkVO> networks = networkApiImpl.list(null, null, null, null, null, null).
                    execute().body();
            if (networks != null && !networks.isEmpty()) {
                device.setNetworkId(networks.get(0).getId());
                deviceApiImpl.register(device, Const.DEVICE_ID).execute().raw().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DeviceUpdate createDevice() {
        DeviceUpdate device = new DeviceUpdate();
        device.setName(Const.NAME);
        device.setId(Const.DEVICE_ID);

        return device;
    }

    private boolean isAlarmTime() {
        return alarmTime != null && alarmTime.isEqual(currentTime);
    }

    private DeviceNotificationWrapper getTimestampNotificationWrapper() {
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
