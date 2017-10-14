package example;

import com.devicehive.client.model.*;
import com.devicehive.client.service.Device;
import com.devicehive.client.service.DeviceCommand;
import com.devicehive.client.service.DeviceHive;
import com.devicehive.rest.model.JsonStringWrapper;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import java.util.Collections;
import java.util.List;

public class Main {
    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String ECHO = "echo";
    private static String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.DVRKVgrtnv35MWwxR1T8bLm83-RJCfloYuoEjvYPQ4s";
    private static String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJSRUZSRVNIIn19.7alYTD5kb_imglE7NyRhjQBFqXhqpfJJs-ZA68yJZiQ";

    private static String DEVICE_ID = "123456-example";
    private static DeviceHive deviceHive = DeviceHive.getInstance()
            .init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));
    private static Device device = deviceHive.getDevice(DEVICE_ID);

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


        CommandFilter commandFilter = new CommandFilter();
        commandFilter.setCommandNames(ECHO);


        System.out.println("Subscribed on ECHO command");

        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            @Override
            public void onSuccess(List<DeviceCommand> commands) {
                System.out.println("Command got success");
                DeviceCommand command = commands.get(0);
                command.setResult(new JsonStringWrapper("SUCCESS"));
                command.updateCommand();
                device.sendNotification(ECHO,
                        Collections.singletonList(new Parameter("command",
                                commands.get(0).getCommandName())));
            }

            @Override
            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });


        NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(ECHO);

        System.out.println("Subscribed on ECHO notification");

        deviceHive.subscribeNotifications(
                Collections.singletonList(DEVICE_ID),
                notificationFilter,
                new DeviceNotificationsCallback() {
                    @Override
                    public void onSuccess(List<DeviceNotification> notifications) {
                        System.out.println("Notification got success");
                    }

                    @Override
                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });
        device.subscribeNotifications(
                notificationFilter,
                new DeviceNotificationsCallback() {
                    @Override
                    public void onSuccess(List<DeviceNotification> notifications) {
                        System.out.println("Notification got success");
                    }

                    @Override
                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("ECHO command sent");
                device.sendCommand(ECHO, Collections.singletonList(new Parameter("TEST", ECHO)));
            }
        }, 5, 10, TimeUnit.SECONDS);
    }
}
