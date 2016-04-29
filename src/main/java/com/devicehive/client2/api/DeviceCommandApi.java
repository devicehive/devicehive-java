package com.devicehive.client2.api;


import retrofit2.Call;
import retrofit2.http.*;

import com.devicehive.client2.model.DeviceCommandWrapper;
import com.devicehive.client2.model.AsyncResponse;

public interface DeviceCommandApi {
  /**
   * Get command 
   * Gets command by device id and command id
   * @param deviceGuid Device GUID (required)
   * @param commandId Command Id (required)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/command/{commandId}")
  Call<Void> get(
    @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId
  );

  /**
   * Insert command
   * Inserts command
   * @param deviceGuid Device GUID (required)
   * @param body Command body (required)
   * @return Call<Void>
   */
  
  @POST("device/{deviceGuid}/command")
  Call<Void> insert(
    @Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body
  );

  /**
   * Poll for commands 
   * Polls for commands based on provided parameters (long polling)
   * @param deviceGuid Device GUID (required)
   * @param names Command names (optional)
   * @param timestamp Timestamp to start from (optional)
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @param body  (optional)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/command/poll")
  Call<Void> poll(
    @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
  );

  /**
   * Poll for commands 
   * Polls for commands based on provided parameters (long polling)
   * @param deviceGuids Device guids (optional)
   * @param names Command names (optional)
   * @param timestamp Timestamp to start from (optional)
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @param body  (optional)
   * @return Call<Void>
   */
  
  @GET("device/command/poll")
  Call<Void> pollMany(
    @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
  );

  /**
   * Query commands 
   * Gets list of commands
   * @param deviceGuid Device GUID (required)
   * @param start Start timestamp (optional)
   * @param end End timestamp (optional)
   * @param command Command name (optional)
   * @param status Command status (optional)
   * @param sortField Sort field (optional)
   * @param sortOrder Sort order (optional)
   * @param take Limit param (optional)
   * @param skip Skip param (optional)
   * @param gridInterval Grid interval (optional)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/command")
  Call<Void> query(
    @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval
  );

  /**
   * Update command
   * Update command
   * @param deviceGuid Device GUID (required)
   * @param commandId Command Id (required)
   * @param body Command body (required)
   * @return Call<Void>
   */
  
  @PUT("device/{deviceGuid}/command/{commandId}")
  Call<Void> update(
    @Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body
  );

  /**
   * Poll for commands 
   * Polls for commands based on provided parameters (long polling)
   * @param deviceGuid Device GUID (required)
   * @param commandId Command Id (required)
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @param body  (optional)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/command/{commandId}/poll")
  Call<Void> wait(
    @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout, @Body AsyncResponse body
  );

}
