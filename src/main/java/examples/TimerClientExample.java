package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.DeviceCommandApi;
import com.devicehive.client.api.DeviceNotificationApi;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.DeviceCommandWrapper;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.JsonStringWrapper;
import com.sun.istack.internal.Nullable;
import retrofit.RetrofitError;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerClientExample {

    static Date timestamp;


    public static void main(String[] args) throws MalformedURLException {

        final ApiClient restClient = new ApiClient(Const.URL, ApiClient.AUTH_API_KEY, Const.MY_API_KEY);
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();


        TimerDeviceExample.registerDevice(restClient);

        final ApiInfo apiInfo = TimerDeviceExample.getApiInfo(restClient);
        timestamp = apiInfo.getServerTimestamp();

        /**FIXME "2015-5-09T14:37:17.404" test data to get list of command
         timestamp = "2015-5-09T14:37:17.404";
         */

//        ses.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {

        Calendar calendar = Calendar.getInstance();
//                calendar.setTime(timestamp);
        calendar.add(Calendar.SECOND, 10);
        setTimer(restClient, Const.ID, true, calendar.getTime());

//            }
//        }, 0, 2, TimeUnit.MINUTES);


        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ApiInfo apiInfo = TimerDeviceExample.getApiInfo(restClient);

                System.out.println("Before");
                pollNotification(restClient,
                        Const.formatTimestamp(apiInfo.getServerTimestamp(), Const.TIMESTAMP_FORMAT));
                System.out.println("After");


            }
        }, 0, 1, TimeUnit.SECONDS);


        try {
            //FIXME PREVENTS APP EXITING delete it if necessary
            System.in.read();
        } catch (RetrofitError | IOException e) {
            e.printStackTrace();

        }

    }

    private static void pollNotification(ApiClient apiClient, String timestamp) {
        DeviceNotificationApi notificationService = apiClient.createService(DeviceNotificationApi.class);
        List<DeviceNotification> deviceNotifications = notificationService.poll(Const.ID, null, timestamp, 30L);
        System.out.println("INSIDE");
        for (DeviceNotification item : deviceNotifications) {
            if (item.getNotification().equals("ALARM"))
                System.out.println(item);
        }

    }

    //Command updating
    private static void setTimer(ApiClient apiClient, String guid, Boolean onTimer, @Nullable Date time) throws RetrofitError {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        if (onTimer) {
            deviceCommandWrapper.setCommand("ON");
            JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
            jsonStringWrapper.setJsonString(Const.formatTimestamp(time, Const.TIMER_FORMAT));
            deviceCommandWrapper.setParameters(jsonStringWrapper.getJsonString());
        }
        DeviceCommandApi commandApi = apiClient.createService(DeviceCommandApi.class);
        commandApi.insert(guid, deviceCommandWrapper);

    }
}

