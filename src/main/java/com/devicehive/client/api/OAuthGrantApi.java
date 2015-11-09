package com.devicehive.client.api;

import retrofit.Callback;
import retrofit.http.*;

import com.devicehive.client.model.OAuthGrant;
import com.devicehive.client.model.OAuthGrantUpdate;

public interface OAuthGrantApi {
  
  /**
   * List oAuth grants
   * Sync method
   * Returns list of oAuth grants for user
   * @param userId User Id
   * @param start Start timestamp
   * @param end End timestamp
   * @param clientOAuthId Client oAuth id
   * @param type Type
   * @param scope Scope
   * @param redirectUri Redirect uri
   * @param accessType Access type
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @return Void
   */
  
  @GET("/user/{userId}/oauth/grant")
  Void list(
    @Path("userId") String userId, @Query("start") String start, @Query("end") String end, @Query("clientOAuthId") String clientOAuthId, @Query("type") String type, @Query("scope") String scope, @Query("redirectUri") String redirectUri, @Query("accessType") String accessType, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List oAuth grants
   * Async method
   * @param userId User Id
   * @param start Start timestamp
   * @param end End timestamp
   * @param clientOAuthId Client oAuth id
   * @param type Type
   * @param scope Scope
   * @param redirectUri Redirect uri
   * @param accessType Access type
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/{userId}/oauth/grant")
  void list(
    @Path("userId") String userId, @Query("start") String start, @Query("end") String end, @Query("clientOAuthId") String clientOAuthId, @Query("type") String type, @Query("scope") String scope, @Query("redirectUri") String redirectUri, @Query("accessType") String accessType, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<Void> cb
  );
  
  /**
   * Create oAuth grant
   * Sync method
   * 
   * @param userId User Id
   * @param body Grant body
   * @return Void
   */
  
  @POST("/user/{userId}/oauth/grant")
  Void insert(
    @Path("userId") String userId, @Body OAuthGrant body
  );

  /**
   * Create oAuth grant
   * Async method
   * @param userId User Id
   * @param body Grant body
   * @param cb callback method
   * @return void
   */
  
  @POST("/user/{userId}/oauth/grant")
  void insert(
    @Path("userId") String userId, @Body OAuthGrant body, Callback<Void> cb
  );
  
  /**
   * Get oAuth grant
   * Sync method
   * Returns oAuth grant by user and id
   * @param userId User Id
   * @param id Grant Id
   * @return Void
   */
  
  @GET("/user/{userId}/oauth/grant/{id}")
  Void get(
    @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Get oAuth grant
   * Async method
   * @param userId User Id
   * @param id Grant Id
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/{userId}/oauth/grant/{id}")
  void get(
    @Path("userId") String userId, @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Update oAuth grant
   * Sync method
   * 
   * @param userId User Id
   * @param id Grant Id
   * @param body Grant body
   * @return Void
   */
  
  @PUT("/user/{userId}/oauth/grant/{id}")
  Void update(
    @Path("userId") String userId, @Path("id") Long id, @Body OAuthGrantUpdate body
  );

  /**
   * Update oAuth grant
   * Async method
   * @param userId User Id
   * @param id Grant Id
   * @param body Grant body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/user/{userId}/oauth/grant/{id}")
  void update(
    @Path("userId") String userId, @Path("id") Long id, @Body OAuthGrantUpdate body, Callback<Void> cb
  );
  
  /**
   * Delete oAuth grant
   * Sync method
   * 
   * @param userId User Id
   * @param id Grant Id
   * @return Void
   */
  
  @DELETE("/user/{userId}/oauth/grant/{id}")
  Void delete(
    @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Delete oAuth grant
   * Async method
   * @param userId User Id
   * @param id Grant Id
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/user/{userId}/oauth/grant/{id}")
  void delete(
    @Path("userId") String userId, @Path("id") Long id, Callback<Void> cb
  );
  
}
