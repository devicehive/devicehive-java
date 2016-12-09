package com.devicehive.client.api;

import com.devicehive.client.model.JwtPayload;
import com.devicehive.client.model.JwtTokenVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JwtTokenApi {
  /**
   * JWT access token request with refresh token
   * 
   * @param refreshToken Refresh token (required)
   * @return Call&lt;JwtTokenVO&gt;
   */
  
  @POST("token/refresh")
  Call<JwtTokenVO> refreshTokenRequest(@Body JwtTokenVO refreshToken);

  /**
   * JWT access and refresh token request
   * 
   * @param payload Payload (required)
   * @return Call&lt;JwtTokenVO&gt;
   */
  
  @POST("token")
  Call<JwtTokenVO> tokenRequest(@Body JwtPayload payload);

}
