import com.devicehive.websocket.api.TokenWS;
import com.devicehive.websocket.api.WebSocketClient;
import com.devicehive.websocket.listener.TokenListener;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String LOGIN = "dhadmin";
    private static final String PASSWORD = "dhadmin_#911";

    WebSocketClient client = new WebSocketClient
            .Builder()
            .url(URL)
            .build();

    @Test
    public void getToken() throws InterruptedException {
        final Object syncObject = new Object();

        TokenWS tokenWS = client.createTokenWS(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {
                Assert.assertTrue(response.getAccessToken() != null);
                Assert.assertTrue(response.getAccessToken().length() > 0);
                System.out.println(response);
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }

            @Override
            public void onCreate(TokenGetResponse response) {

            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }
        });
        tokenWS.get(null, LOGIN, PASSWORD);
        synchronized (syncObject) {
            syncObject.wait();
        }

    }
}
