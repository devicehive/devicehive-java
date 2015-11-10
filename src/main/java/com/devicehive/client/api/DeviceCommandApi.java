package com.devicehive.client.api;

import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandItem;
import com.devicehive.client.model.DeviceCommandWrapper;
import com.devicehive.client.model.AsyncResponse;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

public interface DeviceCommandApi {

    /**
     * Poll for commands
     * Sync method
     * Polls for commands based on provided parameters (long polling)
     *
     * @param deviceGuids Device guids
     * @param names       Command names
     * @param timestamp   Timestamp to start from
     * @param waitTimeout Wait timeout
     * @param body
     * @return Void
     */

    @GET("/device/command/poll")
    Void pollMany(
            @Query("deviceGuids") String deviceGuids,
            @Query("names") String names,
            @Query("timestamp") String timestamp,
            @Query("waitTimeout") Long waitTimeout,
            @Body AsyncResponse body
    );

    /**
     * Poll for commands
     * Async method
     *
     * @param deviceGuids Device guids
     * @param names       Command names
     * @param timestamp   Timestamp to start from
     * @param waitTimeout Wait timeout
     * @param body
     * @param cb          callback method
     * @return void
     */

    @GET("/device/command/poll")
    void pollMany(
            @Query("deviceGuids") String deviceGuids,
            @Query("names") String names,
            @Query("timestamp") String timestamp,
            @Query("waitTimeout") Long waitTimeout,
            @Body AsyncResponse body,
            Callback<Void> cb
    );

    /**
     * Query commands
     * Sync method
     * Gets list of commands
     *
     * @param deviceGuid   Device GUID
     * @param start        Start timestamp
     * @param end          End timestamp
     * @param command      Command name
     * @param status       Command status
     * @param sortField    Sort field
     * @param sortOrder    Sort order
     * @param take         Limit param
     * @param skip         Skip param
     * @param gridInterval Grid interval
     * @return Void
     */

    @GET("/device/{deviceGuid}/command")
    Void query(
            @Path("deviceGuid") String deviceGuid,
            @Query("start") String start,
            @Query("end") String end,
            @Query("command") String command,
            @Query("status") String status,
            @Query("sortField") String sortField,
            @Query("sortOrder") String sortOrder,
            @Query("take") Integer take,
            @Query("skip") Integer skip,
            @Query("gridInterval") Integer gridInterval
    );

    /**
     * Query commands
     * Async method
     *
     * @param deviceGuid   Device GUID
     * @param start        Start timestamp
     * @param end          End timestamp
     * @param command      Command name
     * @param status       Command status
     * @param sortField    Sort field
     * @param sortOrder    Sort order
     * @param take         Limit param
     * @param skip         Skip param
     * @param gridInterval Grid interval
     * @param cb           callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/command")
    void query(
            @Path("deviceGuid") String deviceGuid,
            @Query("start") String start,
            @Query("end") String end,
            @Query("command") String command,
            @Query("status") String status,
            @Query("sortField") String sortField,
            @Query("sortOrder") String sortOrder,
            @Query("take") Integer take,
            @Query("skip") Integer skip,
            @Query("gridInterval") Integer gridInterval,
            Callback<Void> cb
    );

    /**
     * Insert command
     * Sync method
     * Inserts command
     *
     * @param deviceGuid Device GUID
     * @param body       Command body
     * @return Void
     */
    @FormUrlEncoded
    @POST("/device/{deviceGuid}/command")
    DeviceCommand insert(
            @Path("deviceGuid") String deviceGuid,
            @Body DeviceCommandWrapper body
    );

    /**
     * Insert command
     * Async method
     *
     * @param deviceGuid Device GUID
     * @param body       Command body
     * @param cb         callback method
     * @return void
     */
    @FormUrlEncoded
    @POST("/device/{deviceGuid}/command")
    void insert(
            @Path("deviceGuid") String deviceGuid,
            @Body DeviceCommandWrapper body,
            Callback<DeviceCommand> cb
    );

    /**
     * Poll for commands
     * Sync method
     * Polls for commands based on provided parameters (long polling)
     *
     * @param deviceGuid  Device GUID
     * @param names       Command names
     * @param timestamp   Timestamp to start from
     * @param waitTimeout Wait timeout
     * @return Void
     */

    @GET("/device/{deviceGuid}/command/poll")
    List<DeviceCommandItem> poll(
            @Path("deviceGuid") String deviceGuid,
            @Query("names") String names,
            @Query("timestamp") String timestamp,
            @Query("waitTimeout") Long waitTimeout
    );

    /**
     * Poll for commands
     * Async method
     *
     * @param deviceGuid  Device GUID
     * @param names       Command names
     * @param timestamp   Timestamp to start from
     * @param waitTimeout Wait timeout
     * @param cb          callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/command/poll")
    void poll(
            @Path("deviceGuid") String deviceGuid,
            @Query("names") String names,
            @Query("timestamp") String timestamp,
            @Query("waitTimeout") Long waitTimeout,
            Callback<List<DeviceCommandItem>> cb
    );

    /**
     * Get command
     * Sync method
     * Gets command by device id and command id
     *
     * @param deviceGuid Device GUID
     * @param commandId  Command Id
     * @return Void
     */

    @GET("/device/{deviceGuid}/command/{commandId}")
    Void get(
            @Path("deviceGuid") String deviceGuid,
            @Path("commandId") String commandId
    );

    /**
     * Get command
     * Async method
     *
     * @param deviceGuid Device GUID
     * @param commandId  Command Id
     * @param cb         callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/command/{commandId}")
    void get(
            @Path("deviceGuid") String deviceGuid,
            @Path("commandId") String commandId,
            Callback<Void> cb
    );

    /**
     * Update command
     * Sync method
     * Update command
     *
     * @param deviceGuid Device GUID
     * @param commandId  Command Id
     * @param body       Command body
     * @return Void
     */

    @PUT("/device/{deviceGuid}/command/{commandId}")
    Void update(
            @Path("deviceGuid") String deviceGuid,
            @Path("commandId") Long commandId,
            @Body DeviceCommandWrapper body
    );

    /**
     * Update command
     * Async method
     *
     * @param deviceGuid Device GUID
     * @param commandId  Command Id
     * @param body       Command body
     * @param cb         callback method
     * @return void
     */

    @PUT("/device/{deviceGuid}/command/{commandId}")
    void update(
            @Path("deviceGuid") String deviceGuid,
            @Path("commandId") Long commandId,
            @Body DeviceCommandWrapper body,
            Callback<DeviceCommandItem> cb
    );

    /**
     * Poll for commands
     * Sync method
     * Polls for commands based on provided parameters (long polling)
     *
     * @param deviceGuid  Device GUID
     * @param commandId   Command Id
     * @param waitTimeout Wait timeout
     * @param body
     * @return Void
     */

    @GET("/device/{deviceGuid}/command/{commandId}/poll")
    Void wait(
            @Path("deviceGuid") String deviceGuid,
            @Path("commandId") String commandId,
            @Query("waitTimeout") Long waitTimeout,
            @Body AsyncResponse body
    );

    /**
     * Poll for commands
     * Async method
     *
     * @param deviceGuid  Device GUID
     * @param commandId   Command Id
     * @param waitTimeout Wait timeout
     * @param body
     * @param cb          callback method
     * @return void
     */

    @GET("/device/{deviceGuid}/command/{commandId}/poll")
    void wait(
            @Path("deviceGuid") String deviceGuid,
            @Path("commandId") String commandId,
            @Query("waitTimeout") Long waitTimeout,
            @Body AsyncResponse body,
            Callback<Void> cb
    );

}
