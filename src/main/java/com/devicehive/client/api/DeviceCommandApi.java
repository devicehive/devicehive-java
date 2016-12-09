package com.devicehive.client.api;


import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandWrapper;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DeviceCommandApi {
    /**
     * Get command
     * Gets command by device GUID and command id
     * @param deviceGuid Device GUID (required)
     * @param commandId Command Id (required)
     * @return Call&lt;DeviceCommand&gt;
     */

    @GET("device/{deviceGuid}/command/{commandId}")
    Call<DeviceCommand> get(
            @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId
    );

    /**
     * Creates new device command.
     * Creates new device command, stores and returns command with generated id.
     * @param deviceGuid Device GUID (required)
     * @param body Command body (required)
     * @return Call&lt;DeviceCommand&gt;
     */

    @POST("device/{deviceGuid}/command")
    Call<DeviceCommand> insert(
            @Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body
    );

    /**
     * Polls the server to get commands.
     * This method returns all device commands that were created after specified timestamp. In the case when no commands were found, the method blocks until new command is received. If no commands are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     * @param deviceGuid Device GUID (required)
     * @param names Command names (optional)
     * @param timestamp Timestamp to start from (optional)
     * @param waitTimeout Wait timeout in seconds (optional, default to 30)
     * @return Call&lt;List<DeviceCommand>&gt;
     */

    @GET("device/{deviceGuid}/command/poll")
    Call<List<DeviceCommand>> poll(
            @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
    );

    /**
     * Polls the server to get commands.
     * This method returns all device commands that were created after specified timestamp. In the case when no commands were found, the method blocks until new command is received. If no commands are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     * @param deviceGuids List of device GUIDs (optional)
     * @param names Command names (optional)
     * @param timestamp Timestamp to start from (optional)
     * @param waitTimeout Wait timeout in seconds (optional, default to 30)
     * @return Call&lt;List<DeviceCommand>&gt;
     */

    @GET("device/command/poll")
    Call<List<DeviceCommand>> pollMany(
            @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
    );

    /**
     * Query commands.
     * Gets list of commands that has been received in specified time range.
     * @param deviceGuid Device GUID (required)
     * @param start Start timestamp (optional)
     * @param end End timestamp (optional)
     * @param command Command name (optional)
     * @param status Command status (optional)
     * @param sortField Sort field (optional, default to timestamp)
     * @param sortOrder Sort order (optional)
     * @param take Limit param (optional, default to 100)
     * @param skip Skip param (optional, default to 0)
     * @return Call&lt;List<DeviceCommand>&gt;
     */

    @GET("device/{deviceGuid}/command")
    Call<List<DeviceCommand>> query(
            @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
    );

    /**
     * Updates an existing device command.
     * Updates an existing device command.
     * @param deviceGuid Device GUID (required)
     * @param commandId Command Id (required)
     * @param body Command body (required)
     * @return Call&lt;DeviceCommand&gt;
     */

    @PUT("device/{deviceGuid}/command/{commandId}")
    Call<DeviceCommand> update(
            @Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body
    );

    /**
     * Waits for a command to be processed.
     * Waits for a command to be processed.&lt;br&gt;&lt;br&gt;This method returns a command only if it has been processed by a device.&lt;br&gt;&lt;br&gt;In the case when command is not processed, the method blocks until device acknowledges command execution. If the command is not processed within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call.
     * @param deviceGuid Device GUID (required)
     * @param commandId Command Id (required)
     * @param waitTimeout Wait timeout in seconds (default: 30 seconds, maximum: 60 seconds). Specify 0 to disable waiting. (optional, default to 30)
     * @return Call&lt;DeviceCommand&gt;
     */

    @GET("device/{deviceGuid}/command/{commandId}/poll")
    Call<DeviceCommand> wait(
            @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout
    );


}
