import com.devicehive.client.DeviceHive;
import com.devicehive.client.callback.ResponseCallback;
import com.devicehive.client.model.*;
import com.devicehive.client.service.Device;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.Network;
import com.devicehive.rest.model.*;
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

public class Main {

    private static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    private static final String DEVICE_ID = "271990123";
    public static final String DEVICE_NAME = "JAVA LIB TEST";
    public static final String COMMAND_NAME = "TEST_COMMAND";
    public static final String PROP = "TEST_PROP";
    public static final String VALUE = "TEST_VALUE";
    private String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.DVRKVgrtnv35MWwxR1T8bLm83-RJCfloYuoEjvYPQ4s";
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTM2OTI1MTA2NDM1LCJ0b2tlblR5cGUiOiJSRUZSRVNIIn19.7alYTD5kb_imglE7NyRhjQBFqXhqpfJJs-ZA68yJZiQ";
    private DeviceHive deviceHive = DeviceHive.getInstance().setup(URL, new TokenAuth(refreshToken, accessToken));
    private Device device = deviceHive.getDevice(DEVICE_ID);

    @Test
    public void apiInfoTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        deviceHive.getInfo(new ResponseCallback<ApiInfo>() {
            public void onResponse(DHResponse<ApiInfo> response) {
                System.out.println(response);
                Assert.assertTrue(response.isSuccessful());
                latch.countDown();
            }
        });
        latch.await(20, TimeUnit.SECONDS);
        Assert.assertTrue(deviceHive.getInfo().isSuccessful());

    }

    @Test
    public void createToken() throws IOException {
        deviceHive.login("dhadmin", "dhadmin_#911");

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        System.out.println(deviceHive.getTokenAuthService().equals(deviceHive.getTokenConfigurationService()));
        System.out.println(deviceHive.getTokenConfigurationService());
        System.out.println(deviceHive.getTokenAuthService());
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void createTokenViaToken() throws IOException {

        List<String> actions = new ArrayList<String>();
        actions.add("*");
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("*");
        List<String> deviceIds = new ArrayList<String>();
        deviceIds.add("*");
        DateTime dateTime = DateTime.now().plusYears(1);

        DHResponse<JwtToken> response = deviceHive.createToken(actions, 1L, networkIds, deviceIds, dateTime);
        System.out.println(response.toString());
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void refreshToken() throws IOException {
        deviceHive.login("dhadmin", "dhadmin_#911");

        DHResponse<JwtAccessToken> response2 = deviceHive.refreshToken();
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());
    }

    @Test
    public void getConfigurationProperty() throws IOException {
        DHResponse<Configuration> response = deviceHive.getProperty("jwt.secret");
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void setConfigurationProperty() throws IOException {

        DHResponse<Configuration> response = deviceHive.setProperty("jwt.secret2", "device2");

        System.out.println(response);

        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void deleteConfigurationProperty() throws IOException {

        DHResponse<Configuration> response1 = deviceHive.setProperty("jwt.secret2", "device2");
        System.out.println(response1);
        Assert.assertTrue(response1.isSuccessful());

        DHResponse<Void> response2 = deviceHive.removeProperty("jwt.secret2");
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());
    }

    @Test
    public void createNetworkAndDelete() throws IOException {
        DHResponse<Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
        if (response.isSuccessful()) {
            DHResponse<Void> response2 = deviceHive.removeNetwork(response.getData().getId());
            Assert.assertTrue(response2.isSuccessful());
        }
    }

    @Test
    public void getNetwork() throws IOException {
        DHResponse<Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        Assert.assertTrue(response.isSuccessful());
        System.out.println(response);
        DHResponse<NetworkVO> response2 = deviceHive.getNetwork(response.getData().getId());
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());

        DHResponse<Void> response3 = deviceHive.removeNetwork(response.getData().getId());
        Assert.assertTrue(response3.isSuccessful());
    }

    @Test
    public void updateNetwork() throws IOException {
        DHResponse<Network> response = deviceHive.createNetwork("Java Client Lib", "My test network");
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());


        DHResponse<NetworkVO> response2 = deviceHive.getNetwork(response.getData().getId());
        System.out.println(response2);
        Assert.assertTrue(response2.isSuccessful());

        response.getData().setName("Java Client Lib Renamed");
        response.getData().save();
        DHResponse<NetworkVO> response3 = deviceHive.getNetwork(response.getData().getId());
        System.out.println(response3);
        Assert.assertTrue(response3.isSuccessful());
        Assert.assertEquals("Java Client Lib Renamed", response3.getData().getName());

        DHResponse<Void> response4 = deviceHive.removeNetwork(response.getData().getId());
        Assert.assertTrue(response4.isSuccessful());
    }

    @Test
    public void listNetwork() throws IOException {
        NetworkFilter filter = new NetworkFilter();
        filter.setNamePattern("%network%");
        DHResponse<List<Network>> response = deviceHive.listNetworks(filter);
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());

    }

    @Test
    public void createDevice() throws IOException {
    }

    @Test
    public void getCommands() throws IOException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                try {
                    List<Parameter> parameters = new ArrayList<Parameter>();

                    parameters.add(new Parameter("Param 1", "Value 1"));
                    parameters.add(new Parameter("Param 2", "Value 2"));
                    parameters.add(new Parameter("Param 3", "Value 3"));
                    parameters.add(new Parameter("Param 4", "Value 4"));
                    device.sendCommand("Command TEST", parameters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }), 5, TimeUnit.SECONDS);
        System.out.println(device);

        List<DeviceCommand> list =
                device.getCommands(DateTime.now(), DateTime.now().plusMinutes(1), 30);
        System.out.println(list.get(0).getCommandName());

    }

    @Test
    public void subscribeCommands() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final CommandFilter commandFilter = new CommandFilter();
        commandFilter.setCommandNames("comA", "comB");
        commandFilter.setStartTimestamp(DateTime.now());
        commandFilter.setEndTimestamp(DateTime.now().plusSeconds(10));
        commandFilter.setMaxNumber(30);

        device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            public void onSuccess(List<DeviceCommand> command) {
                System.out.println(DateTime.now().toString());
                System.out.println(command);
            }

            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                System.out.println("SUBSCRIBED FOR comZ");
                commandFilter.setCommandNames("comZ");
                device.unsubscribeCommands(commandFilter);
            }
        }), 30, TimeUnit.SECONDS);
        latch.await();
    }

    @Test
    public void subscribeNotifications() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames("notificationA", "notificationB");
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));

        device.subscribeNotifications(notificationFilter, new DeviceNotificationsCallback() {
            public void onSuccess(List<DeviceNotification> command) {
                System.out.println(DateTime.now().toString());
                System.out.println(command);
            }

            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                System.out.println("SUBSCRIBED FOR notificationZ");
                notificationFilter.setNotificationNames("notificationZ");
                device.unsubscribeNotifications(notificationFilter);
            }
        }), 30, TimeUnit.SECONDS);
        latch.await();
    }

    @Test
    public void subscribeManyNotifications() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames("notificationA", "notificationB");
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));
        final List<String> ids = new ArrayList<String>();
        ids.add(DEVICE_ID);
        ids.add("271990");
        deviceHive.subscribeNotifications(ids, notificationFilter, new DeviceNotificationsCallback() {
            public void onSuccess(List<DeviceNotification> command) {
                System.out.println(DateTime.now().toString());
                System.out.println(command);
            }

            public void onFail(FailureData failureData) {
                System.out.println(failureData);
            }
        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                System.out.println("SUBSCRIBED FOR notificationZ");
                notificationFilter.setNotificationNames("notificationZ");
                deviceHive.unsubscribeNotifications(ids, notificationFilter);
            }
        }), 30, TimeUnit.SECONDS);
        latch.await();
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

    @Test
    public void getDevices() {
        DHResponse<List<Device>> devices = deviceHive.listDevices(new DeviceFilter());

    }

    @Test
    public void getNotification() throws IOException {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(new Thread(new Runnable() {
            public void run() {
                try {
                    List<Parameter> parameters = new ArrayList<Parameter>();

                    parameters.add(new Parameter("Param 1", "Value 1"));
                    parameters.add(new Parameter("Param 2", "Value 2"));
                    parameters.add(new Parameter("Param 3", "Value 3"));
                    parameters.add(new Parameter("Param 4", "Value 4"));
                    device.sendNotification("NOTIFICATION MESSAGE", parameters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }), 5, TimeUnit.SECONDS);

        List<DeviceNotification> response = device.getNotifications(DateTime.now(), DateTime.now().plusMinutes(1));
        System.out.println(response);
        Assert.assertTrue(response.size() > 0);
    }
}
