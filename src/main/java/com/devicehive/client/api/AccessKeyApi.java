package com.devicehive.client.api;

import retrofit.Callback;
import retrofit.http.*;

import com.devicehive.client.model.AccessKey;
import com.devicehive.client.model.AccessKeyUpdate;

import java.util.*;

public interface AccessKeyApi {
  
  /**
   * List access keys
   * Sync method
   * Gets list of access keys and their permissions.
   * @param userId User identifier. Use the &#39;current&#39; keyword to list access keys of the current user.
   * @param label Filter by access key label.
   * @param labelPattern Filter by access key label pattern.
   * @param type Filter by access key type.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @return List<AccessKey>
   */
  
  @GET("/user/{userId}/accesskey")
  List<AccessKey> list(
    @Path("userId") String userId, @Query("label") String label, @Query("labelPattern") String labelPattern, @Query("type") Integer type, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List access keys
   * Async method
   * @param userId User identifier. Use the &#39;current&#39; keyword to list access keys of the current user.
   * @param label Filter by access key label.
   * @param labelPattern Filter by access key label pattern.
   * @param type Filter by access key type.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/{userId}/accesskey")
  void list(
    @Path("userId") String userId, @Query("label") String label, @Query("labelPattern") String labelPattern, @Query("type") Integer type, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<List<AccessKey>> cb
  );
  
  /**
   * Create Access key
   * Sync method
   * Creates new access key.
   * @param userId User identifier. Use the &#39;current&#39; keyword to create access key for the current user.
   * @param body Access Key Body
   * @return AccessKey
   */
  
  @POST("/user/{userId}/accesskey")
  AccessKey insert(
    @Path("userId") String userId, @Body AccessKey body
  );

  /**
   * Create Access key
   * Async method
   * @param userId User identifier. Use the &#39;current&#39; keyword to create access key for the current user.
   * @param body Access Key Body
   * @param cb callback method
   * @return void
   */
  
  @POST("/user/{userId}/accesskey")
  void insert(
    @Path("userId") String userId, @Body AccessKey body, Callback<AccessKey> cb
  );
  
  /**
   * Get user&#39;s access key
   * Sync method
   * Gets information about access key and its permissions.
   * @param userId User identifier. Use the &#39;current&#39; keyword to get access key of the current user.
   * @param id Access key identifier.
   * @return AccessKey
   */
  
  @GET("/user/{userId}/accesskey/{id}")
  AccessKey get(
    @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Get user&#39;s access key
   * Async method
   * @param userId User identifier. Use the &#39;current&#39; keyword to get access key of the current user.
   * @param id Access key identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/user/{userId}/accesskey/{id}")
  void get(
    @Path("userId") String userId, @Path("id") Long id, Callback<AccessKey> cb
  );
  
  /**
   * Update Access key
   * Sync method
   * Updates an existing access key.
   * @param userId User identifier. Use the &#39;current&#39; keyword to update access key of the current user.
   * @param id Access key identifier.
   * @param body Access Key Body
   * @return Void
   */
  
  @PUT("/user/{userId}/accesskey/{id}")
  Void update(
    @Path("userId") String userId, @Path("id") Long id, @Body AccessKeyUpdate body
  );

  /**
   * Update Access key
   * Async method
   * @param userId User identifier. Use the &#39;current&#39; keyword to update access key of the current user.
   * @param id Access key identifier.
   * @param body Access Key Body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/user/{userId}/accesskey/{id}")
  void update(
    @Path("userId") String userId, @Path("id") Long id, @Body AccessKeyUpdate body, Callback<Void> cb
  );
  
  /**
   * Delete Access key
   * Sync method
   * Deletes an existing access key.
   * @param userId User identifier. Use the &#39;current&#39; keyword to delete access key of the current user.
   * @param id Access key identifier.
   * @return Void
   */
  
  @DELETE("/user/{userId}/accesskey/{id}")
  Void delete(
    @Path("userId") String userId, @Path("id") Long id
  );

  /**
   * Delete Access key
   * Async method
   * @param userId User identifier. Use the &#39;current&#39; keyword to delete access key of the current user.
   * @param id Access key identifier.
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/user/{userId}/accesskey/{id}")
  void delete(
    @Path("userId") String userId, @Path("id") Long id, Callback<Void> cb
  );
  
}
