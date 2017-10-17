/*
 *
 *
 *   TimerExample.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package examples;

import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.JwtTokenApi;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.model.JwtRequest;
import com.devicehive.rest.model.JwtToken;
import com.devicehive.rest.utils.Const;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerExample {
    public static final String URL = "***REMOVED***/";

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);


        JwtRequest auth = new JwtRequest(Const.LOGIN, Const.PASSWORD);
        final ApiClient client = new ApiClient(URL);

        client.createService(JwtTokenApi.class).login(auth).enqueue(new Callback<JwtToken>() {
            @Override
            public void onResponse(Call<JwtToken> call, final Response<JwtToken> response) {
                if (response.isSuccessful() && response.body() != null) {
                    client.addAuthorization(ApiClient.AUTH_API_KEY,
                            ApiKeyAuth.newInstance(response.body().getAccessToken()));

                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            TimerDevice timerDevice = new TimerDevice(client);
                            try {
                                timerDevice.run();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            TimerClient timerClient = new TimerClient(client);
                            timerClient.run();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<JwtToken> call, Throwable throwable) {
                System.out.println(throwable.toString());
            }
        });


    }
}
