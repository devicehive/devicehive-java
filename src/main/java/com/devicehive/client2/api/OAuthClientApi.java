package com.devicehive.client2.api;

import com.devicehive.client2.model.OAuthClient;
import com.devicehive.client2.model.OAuthClientUpdate;


import retrofit2.Call;
import retrofit2.http.*;

public interface OAuthClientApi {
  /**
   * Delete oAuth client
   * 
   * @param id oAuth client id (required)
   * @return Call<Void>
   */
  
  @DELETE("oauth/client/{id}")
  Call<Void> delete(
    @Path("id") Long id
  );

  /**
   * Get oAuth client
   * Returns oAuth client by id
   * @param id oAuth client id (required)
   * @return Call<Void>
   */
  
  @GET("oauth/client/{id}")
  Call<Void> get(
    @Path("id") Long id
  );

  /**
   * Create oAuth client
   * 
   * @param body oAuth client body (optional)
   * @return Call<Void>
   */
  
  @POST("oauth/client")
  Call<Void> insert(
    @Body OAuthClient body
  );

  /**
   * List oAuth clients
   * 
   * @param name oAuth client name (optional)
   * @param namePattern Name pattern (optional)
   * @param domain oAuth client domain (optional)
   * @param oauthId oAuth client id (optional)
   * @param sortField Sort field (optional)
   * @param sortOrder Sort order (optional)
   * @param take Limit param (optional)
   * @param skip Skip param (optional)
   * @return Call<Void>
   */
  
  @GET("oauth/client")
  Call<Void> list(
    @Query("name") String name, @Query("namePattern") String namePattern, @Query("domain") String domain, @Query("oauthId") String oauthId, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Update oAuth client
   * 
   * @param id oAuth client id (required)
   * @param body oAuth client body (optional)
   * @return Call<Void>
   */
  
  @PUT("oauth/client/{id}")
  Call<Void> update(
    @Path("id") Long id, @Body OAuthClientUpdate body
  );

}
