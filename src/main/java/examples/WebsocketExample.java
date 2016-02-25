package examples;

import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.HiveMessageHandler;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.HiveFactory;
import com.devicehive.client.websocket.api.impl.CommandsAPIWebSocketImpl;
import com.devicehive.client.websocket.api.impl.HiveClientWebSocketImpl;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import org.joda.time.DateTime;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class WebsocketExample {


    public static void main(String[] args) {

        try {
            final HiveClientWebSocketImpl client = HiveFactory.createWSclient(URI.create("http://playground.devicehive.com/api/rest"));
            client.authenticate(Const.API_KEY);


//            ExecutorService service = Executors.newFixedThreadPool(2);
//            service.submit(new Runnable() {
//                @Override
//                public void run() {
//                    try {
            DateTime timestamp = client.getTimestamp();
            Set<String> uuid = new HashSet<String>();
            uuid.add(Const.DEVICE_ID);

            SubscriptionFilter filter = new SubscriptionFilter(uuid, null, timestamp);
            System.out.println(timestamp);

//            NotificationsAPIWebsocketImpl notificationsAPIWebsocket = client.getNotificationsWSAPI();
//            String id = notificationsAPIWebsocket.subscribeForNotifications(filter, new HiveMessageHandler<DeviceNotification>() {
//                @Override
//                public void handle(DeviceNotification message) {
//                    System.out.println("NOTIFICATION\n"+message.toString());
//                }
//            });

            CommandsAPIWebSocketImpl commandsAPIWebSocket = client.getCommandsWSAPI();
            String idCom = commandsAPIWebSocket.subscribeForCommands(filter, new HiveMessageHandler<DeviceCommand>() {
                @Override
                public void handle(DeviceCommand message) {
                    System.out.println("COMMAND\n"+message);
                }
            });

//                    } catch (HiveException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            System.out.println(id);
        } catch (HiveException e) {
            e.printStackTrace();
        }
    }
}
