package example;

import com.github.devicehive.client.model.CommandFilter;
import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.DeviceCommandsCallback;
import com.github.devicehive.client.model.DeviceNotification;
import com.github.devicehive.client.model.DeviceNotificationsCallback;
import com.github.devicehive.client.model.FailureData;
import com.github.devicehive.client.model.NotificationFilter;
import com.github.devicehive.client.model.Parameter;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.rest.model.JsonStringWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    //DeviceHive settings
    private static final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTA4NDI4MjAxMTA4LCJ0IjoxfX0.bYIkp2Gm_fMHcxcaFm6xqB91Fp8C2DdenmdGgsVNqcc";
    private static final String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InUiOjEsImEiOlswXSwibiI6WyIqIl0sImQiOlsiKiJdLCJlIjoxNTI0MTUxMjAxMTA4LCJ0IjowfX0.2wfpmIjrHRtGBoSF3-T77aSAiUYPFSGtgBuGoVZtSxc";

    private static final String URL = "http://playground.dev.devicehive.com";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";

    //Constants
    private static final String SUCCESS = "SUCCESS";
    private static final String PRODUCE_NOTIFICATION = "produce_notification";
    private static final String PING = "ping";
    private static final String UNKNOWN = "unknown";
    private static final String PONG = "pong";

    //Initiating DeviceHive
    private static DeviceHive deviceHive = DeviceHive.getInstance()
            .init(URL, WS_URL, refreshToken, accessToken);


    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        String DEVICE_ID = "123456-example";
        //Device Initiating
        DHResponse<Device> deviceResponse = deviceHive.getDevice(DEVICE_ID);
        if (!deviceResponse.isSuccessful()) {
            System.out.println(deviceResponse);
            return;
        }
        final Device device = deviceResponse.getData();
        //Creating filter to listen commands from the server
        CommandFilter commandFilter = new CommandFilter();
        commandFilter.setCommandNames(PING);

        //Subscribing for commands
        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            @Override
            public void onSuccess(List<DeviceCommand> commands) {
                System.out.println("Command:" + commands.get(0).getCommandName());

                DeviceCommand command = commands.get(0);
                command.setResult(new JsonStringWrapper(SUCCESS));
                command.updateCommand();
                JsonStringWrapper params = commands.get(0).getParameters();

                if (params != null) {
                    //Getting param value
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(params.getJsonString(), JsonObject.class);

                    boolean needToSend = false;
                    if (jsonObject.has(PRODUCE_NOTIFICATION)) {
                        needToSend = jsonObject.get(PRODUCE_NOTIFICATION).getAsBoolean();
                    }

                    if (needToSend) {
                        //Sending notification that will meet filter criteria
                        device.sendNotification(PONG,
                                Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                                        String.valueOf(true))));

                        //Sending notification that won't meet filter criteria
                        device.sendNotification(UNKNOWN,
                                Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                                        String.valueOf(true))));
                    }
                }

            }

            @Override
            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        //Creating filter to listen notification from the server
        NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(PONG);

        //Subscribing for notifications
        deviceHive.subscribeNotifications(
                Collections.singletonList(DEVICE_ID),
                notificationFilter,
                new DeviceNotificationsCallback() {
                    @Override
                    public void onSuccess(List<DeviceNotification> notifications) {
                        System.out.println("Notification:" + notifications.get(0).getNotification());
                    }

                    @Override
                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //Sending command that will meet filter criteria
                device.sendCommand(PING, Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                        String.valueOf(true))));

                //Sending command that won't meet filter criteria
                device.sendCommand(UNKNOWN, Collections.singletonList(new Parameter(PRODUCE_NOTIFICATION,
                        String.valueOf(true))));
            }
        }, 5, 10, TimeUnit.SECONDS);
    }

}
