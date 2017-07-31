package examples;

import com.devicehive.websocket.WSClient;
import com.devicehive.websocket.api.AuthWS;
import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.listener.LoginListener;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.data.DeviceVO;

import java.util.List;

public class WebSocketExample {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";

    public static void main(String[] args) {


        WSClient client = new WSClient
                .Builder()
                .url(URL)
                .build();


        AuthWS loginWS = client.createLoginWS(new LoginListener() {
            @Override
            public void onResponse(JwtTokenResponse response) {
                System.out.println(response);
            }
            @Override
            public void onError(ErrorResponse error) {
                System.out.println("AuthWS:" + error);

            }
        });
        loginWS.getToken("dhadmin", "dhadmin_#911",null);
//        loginWS.authenticate("eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTAxMjM5OTE5MTAxLCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.JPKslV1Hk2n8AU4Gd53S5XxqzFx1O_mn_raL4fo6hus");
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
            public void onError(ErrorResponse error) {
                System.out.println("DeviceListener:" + error);
            }
        };
        DeviceWS deviceWS = client.createDeviceWS(deviceListener);

        deviceWS.list(null, null,null, null,
                null, null,
                null, 0, 0);
        deviceWS.get("441z79GRgY0QnV9HKrLra8Jt2FXRQ6MzqmuP",null);
        deviceWS.delete("1234",null);

    }
}
