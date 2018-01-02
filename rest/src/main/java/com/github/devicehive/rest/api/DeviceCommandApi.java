/*
 *
 *
 *   DeviceCommandApi.java
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

import com.github.devicehive.rest.model.CommandInsert;
import com.github.devicehive.rest.model.DeviceCommandWrapper;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface DeviceCommandApi {
    /**
     * Get command
     * Gets command by device ID and command id
     *
     * @param deviceId  Device ID (required)
     * @param commandId Command Id (required)
     * @return Call&lt;DeviceCommand&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device/{deviceId}/command/{commandId}")
    Call<com.github.devicehive.rest.model.DeviceCommand> get(
            @Path("deviceId") String deviceId, @Path("commandId") String commandId
    );

    /**
     * Creates new device command.
     * Creates new device command, stores and returns command with generated id.
     *
     * @param deviceId Device ID (required)
     * @param body     Command body (required)
     * @return Call&lt;DeviceCommand&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @POST("device/{deviceId}/command")
    Call<CommandInsert> insert(
            @Path("deviceId") String deviceId, @Body DeviceCommandWrapper body
    );

    /**
     * Polls the server to get commands.
     * This method returns all device commands that were created after specified timestamp. In the case when no commands were found, the method blocks until new command is received. If no commands are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     *
     * @param deviceId    Device ID (required)
     * @param names       Command names (optional)
     * @param timestamp   Timestamp to start from (optional)
     * @param waitTimeout Wait timeout in seconds (optional, default to 30)
     * @param limit       Limit number of commands (optional, default to 100)
     * @return Call&lt;List&lt;DeviceCommand&gt;&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device/{deviceId}/command/poll")
    Call<List<com.github.devicehive.rest.model.DeviceCommand>> poll(
            @Path("deviceId") String deviceId, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Query("limit") Integer limit
    );

    /**
     * Polls the server to get commands.
     * This method returns all device commands that were created after specified timestamp. In the case when no commands were found, the method blocks until new command is received. If no commands are received within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call with the same timestamp value.
     *
     * @param deviceIds   List of device IDs (optional)
     * @param names       Command names (optional)
     * @param timestamp   Timestamp to start from (optional)
     * @param waitTimeout Wait timeout in seconds (optional, default to 30)
     * @param limit       Limit number of commands (optional, default to 100)
     * @return Call&lt;List&lt;DeviceCommand&gt;&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device/command/poll")
    Call<List<com.github.devicehive.rest.model.DeviceCommand>> pollMany(
            @Query("deviceId") String deviceIds, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout, @Query("limit") Integer limit
    );

    /**
     * Query commands.
     * Gets list of commands that has been received in specified time range.
     *
     * @param deviceId  Device ID (required)
     * @param start     Start timestamp (optional)
     * @param end       End timestamp (optional)
     * @param command   Command name (optional)
     * @param status    Command status (optional)
     * @param sortField Sort field (optional, default to timestamp)
     * @param sortOrder Sort order (optional)
     * @param take      Limit param (optional, default to 100)
     * @param skip      Skip param (optional, default to 0)
     * @return Call&lt;List&lt;DeviceCommand&gt;&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device/{deviceId}/command")
    Call<List<com.github.devicehive.rest.model.DeviceCommand>> query(
            @Path("deviceId") String deviceId, @Query("start") String start, @Query("end") String end, @Query("command") String command, @Query("status") String status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
    );

    /**
     * Updates an existing device command.
     * Updates an existing device command.
     *
     * @param deviceId  Device ID (required)
     * @param commandId Command Id (required)
     * @param body      Command body (required)
     * @return Call&lt;DeviceCommand&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @PUT("device/{deviceId}/command/{commandId}")
    Call<Void> update(
            @Path("deviceId") String deviceId, @Path("commandId") Long commandId, @Body DeviceCommandWrapper body
    );

    /**
     * Waits for a command to be processed.
     * Waits for a command to be processed.&lt;br&gt;&lt;br&gt;This method returns a command only if it has been processed by a device.&lt;br&gt;&lt;br&gt;In the case when command is not processed, the method blocks until device acknowledges command execution. If the command is not processed within the waitTimeout period, the server returns an empty response. In this case, to continue polling, the client should repeat the call.
     *
     * @param deviceId    Device ID (required)
     * @param commandId   Command Id (required)
     * @param waitTimeout Wait timeout in seconds (default: 30 seconds, maximum: 60 seconds). Specify 0 to disable waiting. (optional, default to 30)
     * @return Call&lt;DeviceCommand&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device/{deviceId}/command/{commandId}/poll")
    Call<com.github.devicehive.rest.model.DeviceCommand> wait(
            @Path("deviceId") String deviceId, @Path("commandId") Long commandId, @Query("waitTimeout") Long waitTimeout
    );

}
