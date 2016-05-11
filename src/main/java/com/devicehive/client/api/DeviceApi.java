package com.devicehive.client.api;


import com.devicehive.client.model.Device;
import com.devicehive.client.model.DeviceEquipment;
import com.devicehive.client.model.DeviceUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceApi {
  /**
   * Delete device
   * Deletes an existing device.
   * @param id Device unique identifier. (required)
   * @return Call<Void>
   */
  
  @DELETE("device/{id}")
  Call<Void> delete(
          @Path("id") String id
  );

  /**
   * Get device&#39;s equipment
   * Gets current state of device equipment.\nThe equipment state is tracked by framework and it could be updated by sending &#39;equipment&#39; notification with the following parameters:\nequipment: equipment code\nparameters: current equipment state
   * @param id Device unique identifier. (required)
   * @return Call<List<DeviceEquipment>>
   */
  
  @GET("device/{id}/equipment")
  Call<List<DeviceEquipment>> equipment(
          @Path("id") String id
  );

  /**
   * Get current state of equipment
   * Gets current state of device equipment by code.
   * @param id Device unique identifier. (required)
   * @param code Equipment code. (required)
   * @return Call<DeviceEquipment>
   */
  
  @GET("device/{id}/equipment/{code}")
  Call<DeviceEquipment> equipmentByCode(
          @Path("id") String id, @Path("code") String code
  );

  /**
   * Get device
   * Gets information about device.
   * @param id Device unique identifier. (required)
   * @return Call<Device>
   */
  
  @GET("device/{id}")
  Call<Device> get(
          @Path("id") String id
  );

  /**
   * List devices
   * Gets list of devices.
   * @param name Filter by device name. (optional)
   * @param namePattern Filter by device name pattern. (optional)
   * @param status Filter by device status. (optional)
   * @param networkId Filter by associated network identifier. (optional)
   * @param networkName Filter by associated network name. (optional)
   * @param deviceClassId Filter by associated device class identifier. (optional)
   * @param deviceClassName Filter by associated device class name. (optional)
   * @param deviceClassVersion Filter by associated device class version. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call<List<Device>>
   */
  
  @GET("device")
  Call<List<Device>> list(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("status") String status, @Query("networkId") Long networkId, @Query("networkName") String networkName, @Query("deviceClassId") Long deviceClassId, @Query("deviceClassName") String deviceClassName, @Query("deviceClassVersion") String deviceClassVersion, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Register device
   * Registers or updates a device. For initial device registration, only &#39;name&#39; and &#39;deviceClass&#39; properties are required.
   * @param body Device body (required)
   * @param id Device unique identifier. (required)
   * @return Call<Void>
   */
  
  @PUT("device/{id}")
  Call<Void> register(
          @Body DeviceUpdate body, @Path("id") String id
  );

}
