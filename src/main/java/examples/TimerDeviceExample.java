package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.api.DeviceApi;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.model.*;
import retrofit.RetrofitError;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerDeviceExample {


    static String timer;
    static boolean isAlarmSet = false;

    public static void main(String[] args) throws MalformedURLException {
        final ApiClient apiClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.MY_API_KEY);
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();


        ApiInfo infoService = getApiInfo(apiClient);
        System.out.println(infoService);

        /**FIXME "2015-5-09T14:37:17.404" test data to get list of command
         timestamp = "2015-5-09T14:37:17.404";
         */
        try {

            registerDevice(apiClient);

            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    sendNotification(apiClient, getTimestampNotificationWrapper());

                }
            }, 0, 30, TimeUnit.SECONDS);


            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    if (isAlarmSet) {
                        Calendar current = Calendar.getInstance();


                        if (timer.equals(Const.formatTimestamp(current.getTime(), Const.TIMER_FORMAT))) {
                            sendNotification(apiClient, getAlarmNotificationWrapper());
                            timer = null;
                            isAlarmSet = false;
                        }
                    } else {
                        final ApiInfo info = getApiInfo(apiClient);
                        DeviceCommandApi commandApi = apiClient.createService(DeviceCommandApi.class);
                        List<DeviceCommand> commands = commandApi.poll(Const.ID,
                                "ON",
                                Const.formatTimestamp(info.getServerTimestamp(), Const.TIMESTAMP_FORMAT),
                                30L);
                        if (commands.size() != 0) {
                            DeviceCommand command = commands.get(0);
                            if (command.getCommand().equals(Const.TIMER_ON_COMMAND)) {
                                timer = command.getParameters();
                                isAlarmSet = true;
                            }
                        }
                    }


                }
            }, 0, 1, TimeUnit.SECONDS);


            //FIXME PREVENTS APP EXITING delete it if necessary
            System.in.read();
        } catch (RetrofitError |
                IOException e
                )

        {
            e.printStackTrace();

        }

    }

    //ApiInfo getting
    public static ApiInfo getApiInfo(ApiClient apiClient) throws RetrofitError {
        ApiInfoApi infoService = apiClient.createService(ApiInfoApi.class);
        return infoService.getApiInfo();
    }


    //Command updating
    private static void sendNotification(ApiClient apiClient, DeviceNotificationWrapper wrapper) throws RetrofitError {
        DeviceNotificationApi notificationApi = apiClient.createService(DeviceNotificationApi.class);
        notificationApi.insert(Const.ID, wrapper);

    }


    public static void registerDevice(ApiClient apiClient) {
        DeviceApi deviceService = apiClient.createService(DeviceApi.class);
        DeviceUpdate device = createDevice();
        deviceService.register(device, Const.ID);

    }


    //Command wrapping
    private static DeviceNotificationWrapper getTimestampNotificationWrapper() throws RetrofitError {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString(Const.formatTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()), Const.TIMER_FORMAT));
        wrapper.setParameters(jsonStringWrapper);
        wrapper.setNotification("Timestamp");
        return wrapper;
    }

    private static DeviceNotificationWrapper getAlarmNotificationWrapper() throws RetrofitError {
        DeviceNotificationWrapper wrapper = new DeviceNotificationWrapper();
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString("BIP BIP BIP");
        wrapper.setParameters(jsonStringWrapper);
        wrapper.setNotification("ALARM");
        return wrapper;
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
