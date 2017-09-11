import com.devicehive.websocket.api.TokenWS;
import com.devicehive.websocket.api.WebSocketClient;
import com.devicehive.websocket.listener.TokenListener;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String LOGIN = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";

    WebSocketClient client = new WebSocketClient
            .Builder()
            .url(URL)
            .build();

    @Test
    public void getToken() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        TokenWS tokenWS = client.createTokenWS(new TokenListener() {
            @Override
            public void onGet(TokenGetResponse response) {
                Assert.assertTrue(response.getAccessToken() != null);
                Assert.assertTrue(response.getAccessToken().length() > 0);
                System.out.println(response);
                latch.countDown();
            }

            @Override
            public void onCreate(TokenGetResponse response) {

            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                latch.countDown();
            }
        });
        tokenWS.get(null, LOGIN, PASSWORD);
        latch.await(30, TimeUnit.SECONDS);

    }
}
