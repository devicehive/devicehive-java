package com.devicehive.client.api;

import com.devicehive.client.model.JwtTokenVO;
import com.devicehive.client.model.OauthJwtRequestVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {


  @POST("oauth/token")
  Call<JwtTokenVO> login(
      @Body OauthJwtRequestVO body
  );

}
