/*
 *
 *
 *   DeviceNotificationApi.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.rest.api;

import com.github.devicehive.rest.model.DeviceNotification;
import com.github.devicehive.rest.model.DeviceNotificationWrapper;
import com.github.devicehive.rest.model.NotificationInsert;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface DeviceNotificationApi {
  /**
   * Get notification
   * Returns notification by device deviceId and notification id
   * @param deviceId Device ID (required)
   * @param id Notification id (required)
   * @return Call&lt;DeviceNotification&gt;
   */
  @GET("device/{deviceId}/notification/{id}")
  Call<DeviceNotification> get(
          @Path("deviceId") String deviceId, @Path("id") Long id);

  /**
   * Create notification
   * Creates notification
   * @param deviceId Device ID (required)
   * @param body Notification body (required)
   * @return Call&lt;NotificationInsert&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("device/{deviceId}/notification")
  Call<NotificationInsert> insert(
          @Path("deviceId") String deviceId, @Body DeviceNotificationWrapper body
  );

  /**
   * Poll for notifications
   * Polls new device notifications for specified device id.  This method returns all device notifications that were created after specified timestamp.  In the case when no notifications were found, the method blocks until new notification is received. If no notifications are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
   * @param deviceId Device ID (required)
   * @param names Notification names (optional)
   * @param timestamp Timestamp to start from (optional)
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @return Call&lt;List&lt;DeviceNotification&gt;&gt;
   */
  @GET("device/{deviceId}/notification/poll")
  Call<List<DeviceNotification>> poll(
          @Path("deviceId") String deviceId,@Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
  );

  /**
   * Poll for notifications
   * Polls new device notifications.  This method returns all device notifications that were created after specified timestamp.  In the case when no notifications were found, the method blocks until new notification is received. If no notifications are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @param deviceIds Device ids (optional)
   * @param names Notification names (optional)
   * @param timestamp Timestamp to start from (optional)
   * @return Call&lt;List&lt;DeviceNotification&gt;&gt;
   */
  @GET("device/notification/poll")
  Call<List<DeviceNotification>> pollMany(
         @Query("deviceIds") String deviceIds, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
  );

  /**
   * Get notifications
   * Returns notifications by provided parameters
   * @param deviceId Device ID (required)
   * @param start Start timestamp (optional)
   * @param end End timestamp (optional)
   * @param notification Notification name (optional)
   * @param sortField Sort field (optional, default to timestamp)
   * @param sortOrder Sort order (optional)
   * @param take Limit param (optional, default to 100)
   * @param skip Skip param (optional, default to 0)
   * @return Call&lt;DeviceNotification&gt;
   */
  @GET("device/{deviceId}/notification")
  Call<List<DeviceNotification>> query(
          @Path("deviceId") String deviceId,  @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

}
