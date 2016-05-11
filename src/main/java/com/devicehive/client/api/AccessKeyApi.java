package com.devicehive.client.api;


import com.devicehive.client.model.AccessKey;
import com.devicehive.client.model.AccessKeyUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccessKeyApi {
  /**
   * Delete Access key
   * Deletes an existing access key.
   * @param userId User identifier. Use the &#39;current&#39; keyword to delete access key of the current user. (required)
   * @param id Access key identifier. (required)
   * @return Call<Void>
   */
  
  @DELETE("user/{userId}/accesskey/{id}")
  Call<Void> delete(
          @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Get user&#39;s access key
   * Gets information about access key and its permissions.
   * @param userId User identifier. Use the &#39;current&#39; keyword to get access key of the current user. (required)
   * @param id Access key identifier. (required)
   * @return Call<AccessKey>
   */
  
  @GET("user/{userId}/accesskey/{id}")
  Call<AccessKey> get(
          @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Create Access key
   * Creates new access key.
   * @param userId User identifier. Use the &#39;current&#39; keyword to create access key for the current user. (required)
   * @param body Access Key Body (required)
   * @return Call<AccessKey>
   */
  
  @POST("user/{userId}/accesskey")
  Call<AccessKey> insert(
          @Path("userId") String userId, @Body AccessKey body
  );

  /**
   * List access keys
   * Gets list of access keys and their permissions.
   * @param userId User identifier. Use the &#39;current&#39; keyword to list access keys of the current user. (required)
   * @param label Filter by access key label. (optional)
   * @param labelPattern Filter by access key label pattern. (optional)
   * @param type Filter by access key type. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call<List<AccessKey>>
   */
  
  @GET("user/{userId}/accesskey")
  Call<List<AccessKey>> list(
          @Path("userId") String userId, @Query("label") String label, @Query("labelPattern") String labelPattern, @Query("type") Integer type, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Update Access key
   * Updates an existing access key.
   * @param userId User identifier. Use the &#39;current&#39; keyword to update access key of the current user. (required)
   * @param id Access key identifier. (required)
   * @param body Access Key Body (required)
   * @return Call<Void>
   */
  
  @PUT("user/{userId}/accesskey/{id}")
  Call<Void> update(
          @Path("userId") String userId, @Path("id") Long id, @Body AccessKeyUpdate body
  );

}
