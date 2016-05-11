package com.devicehive.client.api;

import com.devicehive.client2.model.OAuthGrant;
import com.devicehive.client2.model.OAuthGrantUpdate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OAuthGrantApi {
  /**
   * Delete oAuth grant
   * 
   * @param userId User Id (required)
   * @param id Grant Id (required)
   * @return Call<Void>
   */
  
  @DELETE("user/{userId}/oauth/grant/{id}")
  Call<Void> delete(
          @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Get oAuth grant
   * Returns oAuth grant by user and id
   * @param userId User Id (required)
   * @param id Grant Id (required)
   * @return Call<Void>
   */
  
  @GET("user/{userId}/oauth/grant/{id}")
  Call<Void> get(
          @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Create oAuth grant
   * 
   * @param userId User Id (required)
   * @param body Grant body (required)
   * @return Call<Void>
   */
  
  @POST("user/{userId}/oauth/grant")
  Call<Void> insert(
          @Path("userId") String userId, @Body OAuthGrant body
  );

  /**
   * List oAuth grants
   * Returns list of oAuth grants for user
   * @param userId User Id (required)
   * @param start Start timestamp (optional)
   * @param end End timestamp (optional)
   * @param clientOAuthId Client oAuth id (optional)
   * @param type Type (optional)
   * @param scope Scope (optional)
   * @param redirectUri Redirect uri (optional)
   * @param accessType Access type (optional)
   * @param sortField Sort field (optional)
   * @param sortOrder Sort order (optional)
   * @param take Limit param (optional)
   * @param skip Skip param (optional)
   * @return Call<Void>
   */
  
  @GET("user/{userId}/oauth/grant")
  Call<Void> list(
          @Path("userId") String userId, @Query("start") String start, @Query("end") String end, @Query("clientOAuthId") String clientOAuthId, @Query("type") String type, @Query("scope") String scope, @Query("redirectUri") String redirectUri, @Query("accessType") String accessType, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Update oAuth grant
   * 
   * @param userId User Id (required)
   * @param id Grant Id (required)
   * @param body Grant body (required)
   * @return Call<Void>
   */
  
  @PUT("user/{userId}/oauth/grant/{id}")
  Call<Void> update(
          @Path("userId") String userId, @Path("id") Long id, @Body OAuthGrantUpdate body
  );

}
