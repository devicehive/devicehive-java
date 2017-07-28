package examples;

import com.devicehive.websocket.WSClient;
import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.api.listener.DeviceListener;
import com.devicehive.websocket.api.listener.LoginListener;
import com.devicehive.websocket.model.DeviceVO;
import com.devicehive.websocket.model.ErrorAction;
import com.devicehive.websocket.model.JwtTokenVO;

import java.util.List;

public class WebSocketExample {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";

    public static void main(String[] args) {


        WSClient client = new WSClient.Builder()
                .authParams("***REMOVED***", "***REMOVED***")
//                .token("eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTAxMTYyNzQzMDg3LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.JZXjYUT4y5jrcdvJJzMcQzWw0MwyuVPpPTC0ihQz99k")
                .url(URL)
                .build();

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


        client.addLoginListener(loginListener);
        DeviceListener deviceListener = new DeviceListener() {
            @Override
            public void onDeviceList(List<DeviceVO> response) {
                System.out.println("LIST:" + response);
            }

            @Override
            public void onDeviceGet(DeviceVO response) {
                System.out.println("Single:" + response);
            }

            @Override
            public void onDeviceDelete(List<DeviceVO> response) {

            }

            @Override
            public void onError(ErrorAction error) {
                System.out.println(error);
            }
        };



        DeviceWS deviceWS = client.addDeviceListener(deviceListener);

        deviceWS.list(null, null, null,
                null, null,
                null, 0, 0);
        deviceWS.get("441z79GRgY0QnV9HKrLra8Jt2FXRQ6MzqmuP");
        deviceWS.delete("1234");

    }
}
