package examples;

import com.devicehive.websocket.client.TokenResponseListener;
import com.devicehive.websocket.client.api.DeviceWS;
import com.devicehive.websocket.client.api.LoginWS;
import com.devicehive.websocket.client.model.ErrorAction;
import com.devicehive.websocket.client.model.JwtTokenVO;

public class WebSocketExample {
    public static final String URL = "ws://playground.dev.devicehive.com/api/websocket";

    public static void main(String[] args) {
        final LoginWS login = new LoginWS(URL);
        login.subscribe(new TokenResponseListener() {
            @Override
            public void onResult(JwtTokenVO result) {
                System.out.println(result);
            }

            @Override
            public void onError(ErrorAction error) {
                System.out.println(error);
            }
        });
        login.sendMessage("***REMOVED***", "***REMOVED***");

        DeviceWS deviceWS = new DeviceWS(URL, login.getClient());
        deviceWS.subscribe();
        deviceWS.sendMessage();
        deviceWS.sendMessage();
        deviceWS.sendMessage();
//        login.unsubscribe();
//        deviceWS.subscribe();
//        deviceWS.sendMessage();

    }
}
