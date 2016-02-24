package com.devicehive.client.api;

import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.DeviceNotificationWrapper;
import com.devicehive.client.model.NotificationPollManyResponse;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

public interface DeviceNotificationApi {

    /**
     * Poll for notifications
     * Sync method
     * Polls new device notifications.\n\nThis method returns all device notifications that were created after specified timestamp.\n\nIn the case when no notifications were found, the method blocks until new notification is received. If no notifications are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     *
     * @param waitTimeout Wait timeout
     * @param deviceGuids Device guids
     * @param names       Notification names
     * @param timestamp   Timestamp to start from
     * @return List<NotificationPollManyResponse>
     */

    @GET("/device/notification/poll")
    List<NotificationPollManyResponse> pollMany(
            @Query("waitTimeout") Long waitTimeout, @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp
    );

    /**
     * Poll for notifications
     * Async method
     *
     * @param waitTimeout Wait timeout
     * @param deviceGuids Device guids
     * @param names       Notification names
     * @param timestamp   Timestamp to start from
     * @param cb          callback method
     * @return void
     */

    @GET("/device/notification/poll")
    void pollMany(
            @Query("waitTimeout") Long waitTimeout, @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, Callback<List<NotificationPollManyResponse>> cb
    );

    /**
     * Get notifications
     * Sync method
     * Returns notifications by provided parameters
     *
     * @param deviceGuid   Device GUID
     * @param start        Start timestamp
     * @param end          End timestamp
     * @param notification Notification name
     * @param sortField    Sort field
     * @param sortOrder    Sort order
     * @param take         Limit param
     * @param skip         Skip param
     * @param gridInterval Grid interval
     * @return DeviceNotification
     */

    @GET("/device/{deviceGuid}/notification")
    DeviceNotification query(
            @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval
    );

    /**
     * Get notifications
     * Async method
     *
     * @param deviceGuid   Device GUID
     * @param start        Start timestamp
     * @param end          End timestamp
     * @param notification Notification name
     * @param sortField    Sort field
     * @param sortOrder    Sort order
     * @param take         Limit param
     * @param skip         Skip param
     * @param gridInterval Grid interval
     * @param cb           callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/notification")
    void query(
            @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval, Callback<DeviceNotification> cb
    );

    /**
     * Create notification
     * Sync method
     * Creates notification
     *
     * @param deviceGuid Device GUID
     * @param body       Notification body
     * @return DeviceNotification
     */

    @POST("/device/{deviceGuid}/notification")
    DeviceNotification insert(
            @Path("deviceGuid") String deviceGuid, @Body DeviceNotificationWrapper body
    );

    /**
     * Create notification
     * Async method
     *
     * @param deviceGuid Device GUID
     * @param body       Notification body
     * @param cb         callback method
     * @return void
     */

    @POST("/device/{deviceGuid}/notification")
    void insert(
            @Path("deviceGuid") String deviceGuid, @Body DeviceNotificationWrapper body, Callback<DeviceNotification> cb
    );

    /**
     * Poll for notifications
     * Sync method
     * Polls new device notifications for specified device Guid.\n\nThis method returns all device notifications that were created after specified timestamp.\n\nIn the case when no notifications were found, the method blocks until new notification is received. If no notifications are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     *
     * @param deviceGuid  Device GUID
     * @param names       Notification names
     * @param timestamp   Timestamp to start from
     * @param waitTimeout Wait timeout
     * @return List<DeviceNotification>
     */

    @GET("/device/{deviceGuid}/notification/poll")
    List<DeviceNotification> poll(
            @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
    );

    /**
     * Poll for notifications
     * Async method
     *
     * @param deviceGuid  Device GUID
     * @param names       Notification names
     * @param timestamp   Timestamp to start from
     * @param waitTimeout Wait timeout
     * @param cb          callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/notification/poll")
    void poll(
            @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, Callback<List<DeviceNotification>> cb
    );

    /**
     * Get notification
     * Sync method
     * Returns notification by device guid and notification id
     *
     * @param deviceGuid Device GUID
     * @param id         Notification id
     * @return DeviceNotification
     */

    @GET("/device/{deviceGuid}/notification/{id}")
    DeviceNotification get(
            @Path("deviceGuid") String deviceGuid, @Path("id") Long id
    );

    /**
     * Get notification
     * Async method
     *
     * @param deviceGuid Device GUID
     * @param id         Notification id
     * @param cb         callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/notification/{id}")
    void get(
            @Path("deviceGuid") String deviceGuid, @Path("id") Long id, Callback<DeviceNotification> cb
    );

}
