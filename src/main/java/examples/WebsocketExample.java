package examples;

import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.HiveFactory;
import com.devicehive.client.websocket.api.impl.CommandsAPIWebSocketImpl;
import com.devicehive.client.websocket.api.impl.HiveClientWebSocketImpl;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebsocketExample {


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    HiveClientWebSocketImpl client = HiveFactory.createWSclient(URI.create("http://playground.devicehive.com/api/rest"));

                    client.authenticate(Const.API_KEY);
                    CommandsAPIWebSocketImpl cmd=client.getCommandsWSAPI();




                } catch (HiveException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
