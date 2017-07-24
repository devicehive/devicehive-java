package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.JwtTokenApi;
import com.devicehive.client.model.*;
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

public class WebsocketExample {


    public static void main(String[] args) {

        try {

            ApiClient apiClient = new ApiClient(Const.URL);
            Response<JwtTokenVO> response = apiClient.createService(JwtTokenApi.class)
                    .login(new JwtRequestVO(Const.LOGIN, Const.PASSWORD)).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Bad response " + response.code());
            }

            HiveClientWebSocketImplementation client = HiveFactory.createWSclient(URI.create(Const.URL));
            client.authenticate(response.body().getAccessToken());

            DateTime timestamp = client.getTimestamp();
            Set<String> uuid = new HashSet<>();
            uuid.add(Const.DEVICE_ID);

            SubscriptionFilter filter = new SubscriptionFilter(uuid, null, timestamp);

            NotificationsApiWebSocketImpl notificationsAPIWebsocket = client.getNotificationsWSAPI();
            String id = notificationsAPIWebsocket.subscribeForNotifications(filter, new HiveMessageHandler<DeviceNotification>() {
                @Override
                public void handle(DeviceNotification message) {
                    if (message.getNotification().equals(Const.ALARM)) System.out.println(message.toString());
                }
            });

            CommandsApiWebSocketImpl commandsAPIWebSocket = client.getCommandsWSAPI();

            String idCom = commandsAPIWebSocket.subscribeForCommands(filter, new HiveMessageHandler<DeviceCommand>() {
                @Override
                public void handle(DeviceCommand message) {
                    System.out.println("COMMAND\n" + message);
                }
            });
            System.out.println(idCom);
            DeviceCommand command = new DeviceCommand();
            command.setCommand("ON");
            JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
            jsonStringWrapper.setJsonString(DateTime.now().plusSeconds(5).toString());
            command.setParameters(jsonStringWrapper.toString());

            commandsAPIWebSocket.insertCommand(Const.DEVICE_ID,command, new HiveMessageHandler<DeviceCommand>() {
                @Override
                public void handle(DeviceCommand message) {
                    System.out.println(message.toString());
                }
            });


        } catch (IOException | HiveException e) {
            e.printStackTrace();
        }
    }
}
