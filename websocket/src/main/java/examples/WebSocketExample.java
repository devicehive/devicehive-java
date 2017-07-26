package examples;

import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.api.listener.LoginListener;
import com.devicehive.websocket.WSClient;
import com.devicehive.websocket.api.listener.LoginWS;
import com.devicehive.websocket.model.DeviceVO;
import com.devicehive.websocket.model.ErrorAction;
import com.devicehive.websocket.model.JwtTokenVO;

import java.util.List;

public class WebSocketExample {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";

    public static void main(String[] args) {
        WSClient client = new WSClient(URL);

        LoginListener loginListener = new LoginListener() {
            @Override
            public void onResponse(JwtTokenVO response) {
                System.out.println(response);
            }

            @Override
            public void onError(ErrorAction error) {
                System.out.println(error);

            }
        };
        DeviceListener deviceListener = new DeviceListener() {
            @Override
            public void onDeviceList(List<DeviceVO> response) {
                System.out.println(response);
            }

            @Override
            public void onDeviceGet(DeviceVO response) {
                System.out.println(response);
            }

            @Override
            public void onDeviceDelete(List<DeviceVO> response) {

            }

            @Override
            public void onError(ErrorAction error) {
                System.out.println(error);
            }
        };
        LoginWS loginWS = client.addLoginListener(loginListener);
        DeviceWS deviceWS = client.addDeviceListener(deviceListener);


        loginWS.authenticate("***REMOVED***", "***REMOVED***");
        deviceWS.list(null, null, null,
                null, null,
                null, 0, 0);
        deviceWS.get("websocket-list-commands-1500640479.302747");
    }
}
