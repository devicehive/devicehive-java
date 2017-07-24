package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.JwtTokenApi;
import com.devicehive.client.model.HiveMessageHandler;
import com.devicehive.client.model.JsonStringWrapper;
import com.devicehive.client.model.JwtRequestVO;
import com.devicehive.client.model.JwtTokenVO;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.HiveFactory;
import com.devicehive.client.websocket.api.impl.CommandsApiWebSocketImpl;
import com.devicehive.client.websocket.api.impl.HiveClientWebSocketImplementation;
import com.devicehive.client.websocket.api.impl.NotificationsApiWebSocketImpl;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.websocket.model.DeviceCommand;
import com.devicehive.client.websocket.model.DeviceNotification;
import org.joda.time.DateTime;
import retrofit2.Response;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class WebSocketClient {


    public void run() {
        String token;
        try {
            token = getToken();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return;
        }

        HiveClientWebSocketImplementation client = null;
        try {
            client = HiveFactory.createWSclient(URI.create(Const.URL));
            client.authenticate(token);

            DateTime timestamp = client.getTimestamp();
            SubscriptionFilter filter = createFilter(timestamp);

            NotificationsApiWebSocketImpl notificationsAPIWebsocket = client.getNotificationsWSAPI();
            notificationsAPIWebsocket.subscribeForNotifications(filter, new HiveMessageHandler<DeviceNotification>() {
                @Override
                public void handle(DeviceNotification message) {
                    if (message.getNotification().equals(Const.ALARM)) {
                        System.out.println(message.toString());
                    }
                }
            });

            CommandsApiWebSocketImpl commandsAPIWebSocket = client.getCommandsWSAPI();

        } catch (HiveException e) {
            e.printStackTrace();
        }
    }

    private SubscriptionFilter createFilter(DateTime timestamp) {
        Set<String> uuid = new HashSet<>();
        uuid.add(Const.DEVICE_ID);

        return new SubscriptionFilter(uuid, null, timestamp);
    }

    private String getToken() throws IOException, NullPointerException {
        ApiClient apiClient = new ApiClient(Const.URL);
        Response<JwtTokenVO> response = apiClient.createService(JwtTokenApi.class)
                .login(new JwtRequestVO(Const.LOGIN, Const.PASSWORD)).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Bad response " + response.code());
        }
        String token;
        try {
            token = response.body().getAccessToken();
        } catch (NullPointerException e) {
            throw new RuntimeException("Token is empty");
        }
        return token;
    }

    private void insertCommand(CommandsApiWebSocketImpl commandsAPIWebSocket) throws HiveException {
        DeviceCommand command = getCommand();
        commandsAPIWebSocket.insertCommand(Const.DEVICE_ID, command, new HiveMessageHandler<DeviceCommand>() {
            @Override
            public void handle(DeviceCommand message) {
                System.out.println(message.toString());
            }
        });
    }

    private DeviceCommand getCommand() {
        DeviceCommand command = new DeviceCommand();
        command.setCommand(Const.ON);
        JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
        jsonStringWrapper.setJsonString(DateTime.now().plusSeconds(5).toString());
        command.setParameters(jsonStringWrapper.toString());
        return command;
    }

    private void subscribeForCommands(CommandsApiWebSocketImpl commandsAPIWebSocket, SubscriptionFilter filter) throws HiveException {
        commandsAPIWebSocket.subscribeForCommands(filter, new HiveMessageHandler<DeviceCommand>() {
            @Override
            public void handle(DeviceCommand message) {
                System.out.println("COMMAND\n" + message);
            }
        });
    }

}
