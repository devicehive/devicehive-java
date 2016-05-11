package com.devicehive.client.api;


import com.devicehive.client.model.AsyncResponse;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceCommandApi {
    /**
     * Get command
     * Gets command by device id and command id
     *
     * @param deviceGuid Device GUID (required)
     * @param commandId  Command Id (required)
     * @return Call<Void>
     */

    @GET("device/{deviceGuid}/command/{commandId}")
    Call<DeviceCommand> get(
            @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId
    );

    /**
     * Insert command
     * Inserts command
     *
     * @param deviceGuid Device GUID (required)
     * @param body       Command body (required)
     * @return Call<Void>
     */

    @POST("device/{deviceGuid}/command")
    Call<List<DeviceCommand>> insert(
            @Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body
    );

    /**
     * Poll for commands
     * Polls for commands based on provided parameters (long polling)
     *
     * @param deviceGuid  Device GUID (required)
     * @param names       Command names (optional)
     * @param timestamp   Timestamp to start from (optional)
     * @param waitTimeout Wait timeout (optional, default to 30)
     * @param body        (optional)
     * @return Call<Void>
     */

    @GET("device/{deviceGuid}/command/poll")
    Call<DeviceCommand> poll(
            @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
    );

    /**
     * Poll for commands
     * Polls for commands based on provided parameters (long polling)
     *
     * @param deviceGuids Device guids (optional)
     * @param names       Command names (optional)
     * @param timestamp   Timestamp to start from (optional)
     * @param waitTimeout Wait timeout (optional, default to 30)
     * @param body        (optional)
     * @return Call<Void>
     */

    @GET("device/command/poll")
    Call<List<DeviceCommand>> pollMany(
            @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
    );

    /**
     * Query commands
     * Gets list of commands
     *
     * @param deviceGuid   Device GUID (required)
     * @param start        Start timestamp (optional)
     * @param end          End timestamp (optional)
     * @param command      Command name (optional)
     * @param status       Command status (optional)
     * @param sortField    Sort field (optional)
     * @param sortOrder    Sort order (optional)
     * @param take         Limit param (optional)
     * @param skip         Skip param (optional)
     * @param gridInterval Grid interval (optional)
     * @return Call<Void>
     */

    @GET("device/{deviceGuid}/command")
    Call<List<DeviceCommand>> query(
            @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval
    );

    /**
     * Update command
     * Update command
     *
     * @param deviceGuid Device GUID (required)
     * @param commandId  Command Id (required)
     * @param body       Command body (required)
     * @return Call<Void>
     */

    @PUT("device/{deviceGuid}/command/{commandId}")
    Call<DeviceCommand> update(
            @Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body
    );

    /**
     * Poll for commands
     * Polls for commands based on provided parameters (long polling)
     *
     * @param deviceGuid  Device GUID (required)
     * @param commandId   Command Id (required)
     * @param waitTimeout Wait timeout (optional, default to 30)
     * @param body        (optional)
     * @return Call<Void>
     */

    @GET("device/{deviceGuid}/command/{commandId}/poll")
    Call<List<DeviceCommand>> wait(
            @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
    );

}
