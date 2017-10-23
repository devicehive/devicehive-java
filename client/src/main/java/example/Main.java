package example;

import com.github.devicehive.client.model.*;
import com.github.devicehive.client.service.Device;
import com.github.devicehive.client.service.DeviceCommand;
import com.github.devicehive.client.service.DeviceHive;
import com.github.devicehive.rest.model.JsonStringWrapper;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class Main {

    //DeviceHive settings
    private static final String accessToken = "***REMOVED***";
    private static final String refreshToken = "***REMOVED***";

    private static final String URL = "***REMOVED***";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";

    //Constants
    private static final String SUCCESS = "SUCCESS";
    private static final String PRODUCE_NOTIFICATION = "produce_notification";
    private static final String PING = "ping";
    private static final String UNKNOWN = "unknown";
    private static final String PONG = "pong";

    //Initiating DeviceHive
    private static DeviceHive deviceHive = DeviceHive.getInstance()
            .init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));


    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        String DEVICE_ID = "123456-example";
        //Device Initiating
        final Device device = deviceHive.getDevice(DEVICE_ID);
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
                    JSONObject jsonObject = new JSONObject(params.getJsonString());
                    boolean needToSend = jsonObject.getBoolean(PRODUCE_NOTIFICATION);

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
