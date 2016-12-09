package com.devicehive.client.api;

import com.devicehive.client.model.AccessKeyRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

public interface AuthenticationApi {
  /**
   * Login
   *
   * @param body Access key request (required)
   * @return Call<Void>
   */

  @POST("oauth/token") Call<Void> login(@Body AccessKeyRequest body);

  /**
   * Logout
   *
   * @return Call<Void>
   */

  @DELETE("oauth/token") Call<Void> logout();
}
