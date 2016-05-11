package com.devicehive.client.api;


import com.devicehive.client.model.AccessKey;
import com.devicehive.client.model.AccessKeyRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticationApi {
  /**
   * Access token request
   * 
   * @param grantType Grant type (required)
   * @param code Code (required)
   * @param redirectUri Redirect Uri (required)
   * @param clientId Client Id (required)
   * @param scope Scope (required)
   * @param username User name (Login) (required)
   * @param password Password (required)
   * @return Call<Void>
   */
  
  @FormUrlEncoded
  @POST("oauth2/token")
  Call<AccessKey> accessTokenRequest(
          @Field("grant_type") String grantType, @Field("code") String code, @Field("redirectUri") String redirectUri, @Field("client_id") String clientId, @Field("scope") String scope, @Field("username") String username, @Field("password") String password
  );

  /**
   * Login
   * 
   * @param body Access key request (required)
   * @return Call<Void>
   */
  
  @POST("auth/accesskey")
  Call<Void> login(
          @Body AccessKeyRequest body
  );

  /**
   * Logout
   * 
   * @return Call<Void>
   */
  
  @DELETE("auth/accesskey")
  Call<Void> logout();
    

}
