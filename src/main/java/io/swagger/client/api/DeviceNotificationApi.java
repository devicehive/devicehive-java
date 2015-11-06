package io.swagger.client.api;

import io.swagger.client.CollectionFormats.*;

import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.*;

import io.swagger.client.model.AsyncResponse;
import io.swagger.client.model.DeviceNotificationWrapper;

import java.util.*;

public interface DeviceNotificationApi {
  
  /**
   * Poll for notifications
   * Sync method
   * Polls for notifications based on provided parameters (long polling)
   * @param waitTimeout Wait timeout
   * @param deviceGuids Device guids
   * @param names Notification names
   * @param timestamp Timestamp to start from
   * @param body 
   * @return Void
   */
  
  @GET("/device/notification/poll")
  Void pollMany(
    @Query("waitTimeout") Long waitTimeout, @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Body AsyncResponse body
  );

  /**
   * Poll for notifications
   * Async method
   * @param waitTimeout Wait timeout
   * @param deviceGuids Device guids
   * @param names Notification names
   * @param timestamp Timestamp to start from
   * @param body 
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/notification/poll")
  void pollMany(
    @Query("waitTimeout") Long waitTimeout, @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Body AsyncResponse body, Callback<Void> cb
  );
  
  /**
   * Get notifications
   * Sync method
   * Returns notifications by provided parameters
   * @param deviceGuid Device GUID
   * @param start Start timestamp
   * @param end End timestamp
   * @param notification Notification name
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @param gridInterval Grid interval
   * @return Void
   */
  
  @GET("/device/{deviceGuid}/notification")
  Void query(
    @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval
  );

  /**
   * Get notifications
   * Async method
   * @param deviceGuid Device GUID
   * @param start Start timestamp
   * @param end End timestamp
   * @param notification Notification name
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @param gridInterval Grid interval
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/notification")
  void query(
    @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval, Callback<Void> cb
  );
  
  /**
   * Create notification
   * Sync method
   * Creates notification
   * @param deviceGuid Device GUID
   * @param body Notification body
   * @return Void
   */
  
  @POST("/device/{deviceGuid}/notification")
  Void insert(
    @Path("deviceGuid") String deviceGuid, @Body DeviceNotificationWrapper body
  );

  /**
   * Create notification
   * Async method
   * @param deviceGuid Device GUID
   * @param body Notification body
   * @param cb callback method
   * @return void
   */
  
  @POST("/device/{deviceGuid}/notification")
  void insert(
    @Path("deviceGuid") String deviceGuid, @Body DeviceNotificationWrapper body, Callback<Void> cb
  );
  
  /**
   * Poll for notifications
   * Sync method
   * Polls for notifications based on provided parameters (long polling)
   * @param deviceGuid Device GUID
   * @param names Notification names
   * @param timestamp Timestamp to start from
   * @param waitTimeout Wait timeout
   * @param body 
   * @return Void
   */
  
  @GET("/device/{deviceGuid}/notification/poll")
  Void poll(
    @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
  );

  /**
   * Poll for notifications
   * Async method
   * @param deviceGuid Device GUID
   * @param names Notification names
   * @param timestamp Timestamp to start from
   * @param waitTimeout Wait timeout
   * @param body 
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/notification/poll")
  void poll(
    @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body, Callback<Void> cb
  );
  
  /**
   * Get notification
   * Sync method
   * Returns notification by device guid and notification id
   * @param deviceGuid Device GUID
   * @param id Notification id
   * @return Void
   */
  
  @GET("/device/{deviceGuid}/notification/{id}")
  Void get(
    @Path("deviceGuid") String deviceGuid, @Path("id") Long id
  );

  /**
   * Get notification
   * Async method
   * @param deviceGuid Device GUID
   * @param id Notification id
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/notification/{id}")
  void get(
    @Path("deviceGuid") String deviceGuid, @Path("id") Long id, Callback<Void> cb
  );
  
}
