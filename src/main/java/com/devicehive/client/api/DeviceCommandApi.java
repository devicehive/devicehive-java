package com.devicehive.client.api;

import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceCommandWrapper;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

public interface DeviceCommandApi {
  
  /**
   * Polls the server to get commands.
   * Sync method
   * This method returns all device commands that were created after specified timestamp.\nIn the case when no commands were found, the method blocks until new command is received. If no commands are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
   * @param deviceGuids List of device GUIDs
   * @param names Command names
   * @param timestamp Timestamp to start from
   * @param waitTimeout Wait timeout in seconds
   * @return List<DeviceCommand>
   */
  
  @GET("/device/command/poll")
  List<DeviceCommand> pollMany(
          @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
  );

  /**
   * Polls the server to get commands.
   * Async method
   * @param deviceGuids List of device GUIDs
   * @param names Command names
   * @param timestamp Timestamp to start from
   * @param waitTimeout Wait timeout in seconds
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/command/poll")
  void pollMany(
          @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, Callback<List<DeviceCommand>> cb
  );
  
  /**
   * Query commands.
   * Sync method
   * Gets list of commands that has been received in specified time range.
   * @param deviceGuid Device GUID
   * @param start Start timestamp
   * @param end End timestamp
   * @param command Command name
   * @param status Command status
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @param gridInterval Grid interval
   * @return List<DeviceCommand>
   */
  
  @GET("/device/{deviceGuid}/command")
  List<DeviceCommand> query(
          @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval
  );

  /**
   * Query commands.
   * Async method
   * @param deviceGuid Device GUID
   * @param start Start timestamp
   * @param end End timestamp
   * @param command Command name
   * @param status Command status
   * @param sortField Sort field
   * @param sortOrder Sort order
   * @param take Limit param
   * @param skip Skip param
   * @param gridInterval Grid interval
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/command")
  void query(
          @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval, Callback<List<DeviceCommand>> cb
  );
  
  /**
   * Creates new device command.
   * Sync method
   * Creates new device command, stores and returns command with generated id.
   * @param deviceGuid Device GUID
   * @param body Command body
   * @return DeviceCommand
   */
  
  @POST("/device/{deviceGuid}/command")
  DeviceCommand insert(
          @Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body
  );

  /**
   * Creates new device command.
   * Async method
   * @param deviceGuid Device GUID
   * @param body Command body
   * @param cb callback method
   * @return void
   */
  
  @POST("/device/{deviceGuid}/command")
  void insert(
          @Path("deviceGuid") String deviceGuid, @Body DeviceCommandWrapper body, Callback<DeviceCommand> cb
  );
  
  /**
   * Polls the server to get commands.
   * Sync method
   * This method returns all device commands that were created after specified timestamp.\nIn the case when no commands were found, the method blocks until new command is received. If no commands are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
   * @param deviceGuid Device GUID
   * @param names Command names
   * @param timestamp Timestamp to start from
   * @param waitTimeout Wait timeout in seconds
   * @return List<DeviceCommand>
   */
  
  @GET("/device/{deviceGuid}/command/poll")
  List<DeviceCommand> poll(
          @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
  );

  /**
   * Polls the server to get commands.
   * Async method
   * @param deviceGuid Device GUID
   * @param names Command names
   * @param timestamp Timestamp to start from
   * @param waitTimeout Wait timeout in seconds
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/command/poll")
  void poll(
          @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, Callback<List<DeviceCommand>> cb
  );
  
  /**
   * Get command
   * Sync method
   * Gets command by device GUID and command id
   * @param deviceGuid Device GUID
   * @param commandId Command Id
   * @return DeviceCommand
   */
  
  @GET("/device/{deviceGuid}/command/{commandId}")
  DeviceCommand get(
          @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId
  );

  /**
   * Get command
   * Async method
   * @param deviceGuid Device GUID
   * @param commandId Command Id
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/command/{commandId}")
  void get(
          @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, Callback<DeviceCommand> cb
  );
  
  /**
   * Updates an existing device command.
   * Sync method
   * Updates an existing device command.
   * @param deviceGuid Device GUID
   * @param commandId Command Id
   * @param body Command body
   * @return DeviceCommand
   */
  
  @PUT("/device/{deviceGuid}/command/{commandId}")
  DeviceCommand update(
          @Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body
  );

  /**
   * Updates an existing device command.
   * Async method
   * @param deviceGuid Device GUID
   * @param commandId Command Id
   * @param body Command body
   * @param cb callback method
   * @return void
   */
  
  @PUT("/device/{deviceGuid}/command/{commandId}")
  void update(
          @Path("deviceGuid") String deviceGuid, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body, Callback<DeviceCommand> cb
  );
  
  /**
   * Waits for a command to be processed.
   * Sync method
   * Waits for a command to be processed.\n\nThis method returns a command only if it has been processed by a device.\n\nIn the case when command is not processed, the method blocks until device acknowledges command execution. If the command is not processed within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call.
   * @param deviceGuid Device GUID
   * @param commandId Command Id
   * @param waitTimeout Wait timeout in seconds
   * @return List<DeviceCommand>
   */
  
  @GET("/device/{deviceGuid}/command/{commandId}/poll")
  List<DeviceCommand> wait(
          @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout
  );

  /**
   * Waits for a command to be processed.
   * Async method
   * @param deviceGuid Device GUID
   * @param commandId Command Id
   * @param waitTimeout Wait timeout in seconds
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{deviceGuid}/command/{commandId}/poll")
  void wait(
          @Path("deviceGuid") String deviceGuid, @Path("commandId") String commandId, @Query("waitTimeout") Long waitTimeout, Callback<List<DeviceCommand>> cb
  );
  
}
