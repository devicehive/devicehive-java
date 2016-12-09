package com.devicehive.client.api;


import com.devicehive.client.model.OAuthClientUpdate;
import com.devicehive.client.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
  Call<User> list(
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
