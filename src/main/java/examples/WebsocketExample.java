package examples;

import com.devicehive.client.websocket.api.impl.HiveClientWebsocketImpl;
import com.devicehive.client.websocket.context.WebsocketAgent;
import com.devicehive.client.websocket.model.exceptions.HiveException;

import java.net.URI;

public class WebsocketExample {


    public static void main(String[] args) {

        try {
            WebsocketAgent agent = new WebsocketAgent(URI.create("http://192.168.57.101:8080/dh/api"));
            agent.connect();
            HiveClientWebsocketImpl client = new HiveClientWebsocketImpl(agent);
            client.authenticate("***REMOVED***","***REMOVED***");

        } catch (HiveException e) {
            e.printStackTrace();
        }
    }
}
