import com.devicehive.client.DeviceHive;
import com.devicehive.client.model.*;
import com.devicehive.client.service.Device;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceTest {

    private static final String DEVICE_ID = "271990123";

    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    private static final String WS_URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String NOTIFICATION_A = "notificationA";
    private static final String NOTIFICATION_B = "notificationB";
    private static final String NOTIFICATION_Z = "notificationZ";
    private static final String COM_A = "comA";
    private static final String COM_B = "comB";
    private static final String COM_Z = "comZ";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.DVRKVgrtnv35MWwxR1T8bLm83-RJCfloYuoEjvYPQ4s";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJSRUZSRVNIIn19.7alYTD5kb_imglE7NyRhjQBFqXhqpfJJs-ZA68yJZiQ";


    private DeviceHive deviceHive = DeviceHive.getInstance().init(URL, WS_URL, new TokenAuth(refreshToken, accessToken));

    private Device device = deviceHive.getDevice(DEVICE_ID);


    @Test
    public void createDevice() throws IOException {
    }

    @Test
    public void getCommands() throws IOException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendCommand("Command TEST", parameters, new DeviceCommandCallback() {
                    public void onSuccess(DeviceCommand command) {
                        System.out.println(command);
                    }

                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });
            }
        }), 5, TimeUnit.SECONDS);

        List<DeviceCommand> list =
                device.getCommands(DateTime.now(), DateTime.now().plusMinutes(1), 30);
    }

    @Test
    public void subscribeCommands() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        final CountDownLatch latchZ = new CountDownLatch(1);

        final CommandFilter commandFilter = new CommandFilter();
        commandFilter.setCommandNames(COM_A, COM_B);
        commandFilter.setStartTimestamp(DateTime.now());
        commandFilter.setEndTimestamp(DateTime.now().plusSeconds(10));
        commandFilter.setMaxNumber(30);

        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendCommand(COM_Z, parameters, new DeviceCommandCallback() {
                    public void onSuccess(DeviceCommand command) {
                    }

                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });
            }
        }), 10, TimeUnit.SECONDS);

        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));

                device.sendCommand(COM_A, parameters, null);
                device.sendCommand(COM_B, parameters, new DeviceCommandCallback() {
                    public void onSuccess(DeviceCommand command) {
                    }

                    public void onFail(FailureData failureData) {
                        System.out.println(failureData);
                    }
                });
                device.sendCommand(COM_Z, parameters, null);
            }
        }), 5, TimeUnit.SECONDS);

        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            public void onSuccess(List<DeviceCommand> commands) {
                for (DeviceCommand command :
                        commands) {
                    if (command.getCommandName().equals(COM_A)) {
                        latch.countDown();
                        Assert.assertTrue(true);
                    } else if (command.getCommandName().equals(COM_B)) {
                        Assert.assertTrue(true);
                        latch.countDown();
                    } else if (command.getCommandName().equals(COM_Z)) {
                        Assert.assertTrue(true);

                        latchZ.countDown();
                    }
                }
            }

            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });

        latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue(latch.getCount() == 0);
        commandFilter.setCommandNames(COM_Z);
        device.unsubscribeCommands(commandFilter);
        latchZ.await(60, TimeUnit.SECONDS);
        Assert.assertTrue(latchZ.getCount() == 0);
        device.unsubscribeAllCommands();
    }

    @Test
    public void subscribeNotifications() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);

        final NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(NOTIFICATION_A, NOTIFICATION_B);
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));


        device.subscribeNotifications(notificationFilter, new DeviceNotificationsCallback() {

            public void onSuccess(List<DeviceNotification> notifications) {
                for (DeviceNotification notification :
                        notifications) {
                    if (notification.getNotification().equals(NOTIFICATION_A)) {
                        latch.countDown();
                        Assert.assertTrue(true);
                    } else if (notification.getNotification().equals(NOTIFICATION_B)) {
                        Assert.assertTrue(true);
                        latch.countDown();
                    } else if (notification.getNotification().equals(NOTIFICATION_Z)) {
                        Assert.assertTrue(true);
                        latch.countDown();
                    }
                }
            }

            public void onFail(FailureData failureData) {
            }
        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                notificationFilter.setNotificationNames(NOTIFICATION_Z);
                device.unsubscribeNotifications(notificationFilter);
            }
        }), 20, TimeUnit.SECONDS);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification(NOTIFICATION_Z, parameters);
            }
        }), 25, TimeUnit.SECONDS);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification(NOTIFICATION_A, parameters);
                device.sendNotification(NOTIFICATION_B, parameters);
                device.sendNotification(NOTIFICATION_Z, parameters);
            }
        }), 10, TimeUnit.SECONDS);
        latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue(latch.getCount() == 0);
    }


    @Test
    public void getNotification() throws IOException {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                List<Parameter> parameters = new ArrayList<Parameter>();

                parameters.add(new Parameter("Param 1", "Value 1"));
                parameters.add(new Parameter("Param 2", "Value 2"));
                parameters.add(new Parameter("Param 3", "Value 3"));
                parameters.add(new Parameter("Param 4", "Value 4"));
                device.sendNotification("NOTIFICATION MESSAGE", parameters);
            }
        }), 5, TimeUnit.SECONDS);

        List<DeviceNotification> response = device.getNotifications(DateTime.now(), DateTime.now().plusMinutes(1));
        Assert.assertTrue(response.size() > 0);
    }

    @Test
    public void sendNotification() throws IOException {


        List<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new Parameter("Param 1", "Value 1"));
        parameters.add(new Parameter("Param 2", "Value 2"));
        parameters.add(new Parameter("Param 3", "Value 3"));
        parameters.add(new Parameter("Param 4", "Value 4"));

        DHResponse<DeviceNotification> response = device.sendNotification("NOTIFICATION MESSAGE", parameters);
        Assert.assertTrue(response.isSuccessful());
    }


}
