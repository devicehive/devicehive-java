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
                System.out.println("LIST:"+response);
            }

            @Override
            public void onDeviceGet(DeviceVO response) {
                System.out.println("Single:"+response);
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


//        loginWS.authenticate("dhadmin", "dhadmin_#911");
        loginWS.authenticate("eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTAxMDgyMTEwMTE3LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.5ybd_KzbOnOdAAcGhRDSLiy9raM1LneTbtkO9iO7Cxs");
        deviceWS.list(null, null, null,
                null, null,
                null, 0, 0);
        deviceWS.get("441z79GRgY0QnV9HKrLra8Jt2FXRQ6MzqmuP");
    }
}
