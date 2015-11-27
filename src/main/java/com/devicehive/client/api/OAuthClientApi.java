package com.devicehive.client.api;

import com.devicehive.client.model.OAuthClient;
import com.devicehive.client.model.OAuthClientUpdate;
import retrofit.Callback;
import retrofit.http.*;

public interface OAuthClientApi {
  
  /**
   * List oAuth clients
   * Sync method
   * 
   * @param name oAuth client name
   * @param namePattern Name pattern
   * @param domain oAuth client domain
   * @param oauthId oAuth client id
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @return Void
   */
  
  @GET("/oauth/client")
  Void list(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("domain") String domain, @Query("oauthId") String oauthId, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List oAuth clients
   * Async method
   * @param name oAuth client name
   * @param namePattern Name pattern
   * @param domain oAuth client domain
   * @param oauthId oAuth client id
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @param cb callback method
   * @return void
   */
  
  @GET("/oauth/client")
  void list(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("domain") String domain, @Query("oauthId") String oauthId, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<Void> cb
  );
  
  /**
   * Create oAuth client
   * Sync method
   * 
   * @param body oAuth client body
   * @return Void
   */
  
  @POST("/oauth/client")
  Void insert(
          @Body OAuthClient body
  );

  /**
   * Create oAuth client
   * Async method
   * @param body oAuth client body
   * @param cb callback method
   * @return void
   */
  
  @POST("/oauth/client")
  void insert(
          @Body OAuthClient body, Callback<Void> cb
  );
  
  /**
   * Get oAuth client
   * Sync method
   * Returns oAuth client by id
   * @param id oAuth client id
   * @return Void
   */
  
  @GET("/oauth/client/{id}")
  Void get(
          @Path("id") Long id
  );

  /**
   * Get oAuth client
   * Async method
   * @param id oAuth client id
   * @param cb callback method
   * @return void
   */
  
  @GET("/oauth/client/{id}")
  void get(
          @Path("id") Long id, Callback<Void> cb
  );
  
  /**
   * Update oAuth client
   * Sync method
   * 
   * @param id oAuth client id
   * @param body oAuth client body
   * @return Void
   */
  
  @PUT("/oauth/client/{id}")
  Void update(
          @Path("id") Long id, @Body OAuthClientUpdate body
  );

  /**
   * Update oAuth client
   * Async method
   * @param id oAuth client id
   * @param body oAuth client body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/oauth/client/{id}")
  void update(
          @Path("id") Long id, @Body OAuthClientUpdate body, Callback<Void> cb
  );
  
  /**
   * Delete oAuth client
   * Sync method
   * 
   * @param id oAuth client id
   * @return Void
   */
  
  @DELETE("/oauth/client/{id}")
  Void delete(
          @Path("id") Long id
  );

  /**
   * Delete oAuth client
   * Async method
   * @param id oAuth client id
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/oauth/client/{id}")
  void delete(
          @Path("id") Long id, Callback<Void> cb
  );
  
}
