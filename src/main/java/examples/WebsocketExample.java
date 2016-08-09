package examples;

import com.devicehive.client.model.*;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.HiveFactory;
import com.devicehive.client.websocket.api.impl.CommandsApiWebSocketImpl;
import com.devicehive.client.websocket.api.impl.HiveClientWebSocketImplementation;
import com.devicehive.client.websocket.api.impl.NotificationsApiWebSocketImpl;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import org.joda.time.DateTime;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class WebsocketExample {


    public static void main(String[] args) {

        try {
            final HiveClientWebSocketImplementation client = HiveFactory.createWSclient(URI.create("http://playground.devicehive.com/api/rest"));
            client.authenticate(Const.API_KEY);

            DateTime timestamp = client.getTimestamp();
            Set<String> uuid = new HashSet<>();
            uuid.add(Const.DEVICE_ID);

            SubscriptionFilter filter = new SubscriptionFilter(uuid, null, timestamp);
            System.out.println(timestamp);

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

//            DeviceCommand command = new DeviceCommand();
//            command.setCommand("WEBSOCKET");
//            command.setParameters("SENT");
//
//            commandsAPIWebSocket.updateCommand(Const.DEVICE_ID, command);
        } catch (HiveException e) {
            e.printStackTrace();
        }
    }
}
