package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.JwtTokenApi;
import com.devicehive.client.model.JwtRequestVO;
import com.devicehive.client.model.JwtTokenVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerExample {


    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);


        JwtRequestVO auth = new JwtRequestVO();
        auth.setLogin(Const.LOGIN);
        auth.setPassword(Const.PASSWORD);
        ApiClient client = new ApiClient(Const.URL);

        client.createService(JwtTokenApi.class).login(auth).enqueue(new Callback<JwtTokenVO>() {
            @Override
            public void onResponse(Call<JwtTokenVO> call, final Response<JwtTokenVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            TimerDevice device = new TimerDevice(response.body().getAccessToken());
                            try {
                                device.run();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            TimerClient client = new TimerClient(response.body().getAccessToken());
                            client.run();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<JwtTokenVO> call, Throwable throwable) {
                System.out.println(throwable.toString());
            }
        });


    }
}
