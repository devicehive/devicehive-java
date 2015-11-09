package com.devicehive.client.api;

import retrofit.Callback;
import retrofit.http.*;

import com.devicehive.client.model.AccessKeyRequest;

public interface AuthenticationApi {
  
  /**
   * Login
   * Sync method
   * 
   * @param body Access key request
   * @return Void
   */
  
  @POST("/auth/accesskey")
  Void login(
    @Body AccessKeyRequest body
  );

  /**
   * Login
   * Async method
   * @param body Access key request
   * @param cb callback method
   * @return void
   */
  
  @POST("/auth/accesskey")
  void login(
    @Body AccessKeyRequest body, Callback<Void> cb
  );
  
  /**
   * Logout
   * Sync method
   * 
   * @return Void
   */
  
  @DELETE("/auth/accesskey")
  Void logout();
    

  /**
   * Logout
   * Async method
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/auth/accesskey")
  void logout(
    Callback<Void> cb
  );
  
  /**
   * Access token request
   * Sync method
   * 
   * @param grantType Grant type
   * @param code Code
   * @param redirectUri Redirect Uri
   * @param clientId Client Id
   * @param scope Scope
   * @param username User name (Login)
   * @param password Password
   * @return Void
   */
  
  @FormUrlEncoded
  @POST("/oauth2/token")
  Void accessTokenRequest(
    @Field("grant_type") String grantType, @Field("code") String code, @Field("redirectUri") String redirectUri, @Field("client_id") String clientId, @Field("scope") String scope, @Field("username") String username, @Field("password") String password
  );

  /**
   * Access token request
   * Async method
   * @param grantType Grant type
   * @param code Code
   * @param redirectUri Redirect Uri
   * @param clientId Client Id
   * @param scope Scope
   * @param username User name (Login)
   * @param password Password
   * @param cb callback method
   * @return void
   */
  
  @FormUrlEncoded
  @POST("/oauth2/token")
  void accessTokenRequest(
    @Field("grant_type") String grantType, @Field("code") String code, @Field("redirectUri") String redirectUri, @Field("client_id") String clientId, @Field("scope") String scope, @Field("username") String username, @Field("password") String password, Callback<Void> cb
  );
  
}
