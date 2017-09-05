import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.model.JwtRequestVO;
import com.devicehive.rest.model.JwtTokenVO;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

public class JwtTokenApiTest extends TestHelper {


    @Test
    public void getToken() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequestVO requestBody = new JwtRequestVO();
        requestBody.setLogin("***REMOVED***");
        requestBody.setPassword("***REMOVED***");

        Response<JwtTokenVO> response = api.login(requestBody).execute();
        Assert.assertTrue(response.isSuccessful());
        JwtTokenVO tokenVO = response.body();
        Assert.assertTrue(tokenVO != null);
        Assert.assertTrue(tokenVO.getAccessToken() != null);
        Assert.assertTrue(tokenVO.getAccessToken().length() > 0);
    }

    @Test
    public void getTokenIncorrectCredentials() throws IOException {
        JwtTokenApi api = client.createService(JwtTokenApi.class);
        JwtRequestVO requestBody = new JwtRequestVO();
        requestBody.setLogin("incorrectLogin");
        requestBody.setPassword("incorrectPassword");
        Response<JwtTokenVO> response = api.login(requestBody).execute();
        Assert.assertTrue(!response.isSuccessful());
        Assert.assertTrue(response.body() == null);
    }


}
