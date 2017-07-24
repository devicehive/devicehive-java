package com.devicehive.rest.api;

import com.devicehive.rest.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface JwtTokenApi {
  /**
   * Login
   * Authenticates a user and returns a session-level JWT token.
   * @param body Access key request (required)
   * @return Call&lt;JwtTokenVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("token")
  Call<JwtTokenVO> login(
          @Body JwtRequestVO body
  );

  /**
   * JWT access token request with refresh token
   *
   * @param refreshToken Refresh token (required)
   * @return Call&lt;JwtAccessTokenVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("token/refresh")
  Call<JwtAccessTokenVO> refreshTokenRequest(
          @Body JwtRefreshTokenVO refreshToken
  );

  /**
   * JWT access and refresh token request
   *
   * @param payload Payload (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;JwtTokenVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("token/create")
  Call<JwtTokenVO> tokenRequest(
          @Body JwtPayload payload, @Header("Authorization") String authorization
  );

}
