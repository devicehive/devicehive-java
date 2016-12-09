package com.devicehive.client.api;

import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.DeviceNotificationWrapper;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DeviceNotificationApi {
    /**
     * Get notification
     * Returns notification by device guid and notification id
     *
     * @param deviceGuid Device GUID (required)
     * @param id         Notification id (required)
     * @return Call&lt;DeviceNotification&gt;
     */

    @GET("device/{deviceGuid}/notification/{id}")
    Call<DeviceNotification> get(
            @Path("deviceGuid") String deviceGuid, @Path("id") Long id
    );

    /**
     * Create notification
     * Creates notification
     *
     * @param deviceGuid Device GUID (required)
     * @param body       Notification body (required)
     * @return Call&lt;DeviceNotification&gt;
     */

    @POST("device/{deviceGuid}/notification")
    Call<DeviceNotification> insert(
            @Path("deviceGuid") String deviceGuid, @Body DeviceNotificationWrapper body
    );

    /**
     * Poll for notifications
     * Polls new device notifications for specified device Guid.  This method returns all device notifications that were created after specified timestamp.  In the case when no notifications were found, the method blocks until new notification is received. If no notifications are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     *
     * @param deviceGuid  Device GUID (required)
     * @param names       Notification names (optional)
     * @param timestamp   Timestamp to start from (optional)
     * @param waitTimeout Wait timeout (optional, default to 30)
     * @return Call&lt;List<DeviceNotification>&gt;
     */

    @GET("device/{deviceGuid}/notification/poll")
    Call<List<DeviceNotification>> poll(
            @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
    );

    /**
     * Poll for notifications
     * Polls new device notifications.  This method returns all device notifications that were created after specified timestamp.  In the case when no notifications were found, the method blocks until new notification is received. If no notifications are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     *
     * @param waitTimeout Wait timeout (optional, default to 30)
     * @param deviceGuids Device guids (optional)
     * @param names       Notification names (optional)
     * @param timestamp   Timestamp to start from (optional)
     * @return Call&lt;List<DeviceNotification>&gt;
     */

    @GET("device/notification/poll")
    Call<List<DeviceNotification>> pollMany(
            @Query("waitTimeout") Long waitTimeout, @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp
    );

    /**
     * Get notifications
     * Returns notifications by provided parameters
     *
     * @param deviceGuid   Device GUID (required)
     * @param start        Start timestamp (optional)
     * @param end          End timestamp (optional)
     * @param notification Notification name (optional)
     * @param sortField    Sort field (optional, default to timestamp)
     * @param sortOrder    Sort order (optional)
     * @param take         Limit param (optional, default to 100)
     * @param skip         Skip param (optional, default to 0)
     * @return Call&lt;DeviceNotification&gt;
     */

    @GET("device/{deviceGuid}/notification")
    Call<DeviceNotification> query(
            @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
    );


}
