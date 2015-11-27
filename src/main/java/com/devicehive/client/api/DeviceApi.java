package com.devicehive.client.api;

import com.devicehive.client.model.Device;
import com.devicehive.client.model.DeviceEquipment;
import com.devicehive.client.model.DeviceUpdate;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

public interface DeviceApi {
  
  /**
   * List devices
   * Sync method
   * Gets list of devices.
   * @param name Filter by device name.
   * @param namePattern Filter by device name pattern.
   * @param status Filter by device status.
   * @param networkId Filter by associated network identifier.
   * @param networkName Filter by associated network name.
   * @param deviceClassId Filter by associated device class identifier.
   * @param deviceClassName Filter by associated device class name.
   * @param deviceClassVersion Filter by associated device class version.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @return List<Device>
   */
  
  @GET("/device")
  List<Device> list(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("status") String status, @Query("networkId") Long networkId, @Query("networkName") String networkName, @Query("deviceClassId") Long deviceClassId, @Query("deviceClassName") String deviceClassName, @Query("deviceClassVersion") String deviceClassVersion, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * List devices
   * Async method
   * @param name Filter by device name.
   * @param namePattern Filter by device name pattern.
   * @param status Filter by device status.
   * @param networkId Filter by associated network identifier.
   * @param networkName Filter by associated network name.
   * @param deviceClassId Filter by associated device class identifier.
   * @param deviceClassName Filter by associated device class name.
   * @param deviceClassVersion Filter by associated device class version.
   * @param sortField Result list sort field.
   * @param sortOrder Result list sort order.
   * @param take Number of records to take from the result list.
   * @param skip Number of records to skip from the result list.
   * @param cb callback method
   * @return void
   */
  
  @GET("/device")
  void list(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("status") String status, @Query("networkId") Long networkId, @Query("networkName") String networkName, @Query("deviceClassId") Long deviceClassId, @Query("deviceClassName") String deviceClassName, @Query("deviceClassVersion") String deviceClassVersion, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, Callback<List<Device>> cb
  );
  
  /**
   * Get device
   * Sync method
   * Gets information about device.
   * @param id Device unique identifier.
   * @return Void
   */
  
  @GET("/device/{id}")
  Void get(
          @Path("id") String id
  );

  /**
   * Get device
   * Async method
   * @param id Device unique identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{id}")
  void get(
          @Path("id") String id, Callback<Void> cb
  );
  
  /**
   * Register device
   * Sync method
   * Registers or updates a device. For initial device registration, only &#39;name&#39; and &#39;deviceClass&#39; properties are required.
   * @param body Device body
   * @param id Device unique identifier.
   * @return Void
   */
  
  @PUT("/device/{id}")
  Void register(
          @Body DeviceUpdate body, @Path("id") String id
  );

  /**
   * Register device
   * Async method
   * @param body Device body
   * @param id Device unique identifier.
   * @param cb callback method
   * @return void
   */
  
  @PUT("/device/{id}")
  void register(
          @Body DeviceUpdate body, @Path("id") String id, Callback<Void> cb
  );
  
  /**
   * Delete device
   * Sync method
   * Deletes an existing device.
   * @param id Device unique identifier.
   * @return Void
   */
  
  @DELETE("/device/{id}")
  Void delete(
          @Path("id") String id
  );

  /**
   * Delete device
   * Async method
   * @param id Device unique identifier.
   * @param cb callback method
   * @return void
   */
  
  @DELETE("/device/{id}")
  void delete(
          @Path("id") String id, Callback<Void> cb
  );
  
  /**
   * Get device&#39;s equipment
   * Sync method
   * Gets current state of device equipment.\nThe equipment state is tracked by framework and it could be updated by sending &#39;equipment&#39; notification with the following parameters:\nequipment: equipment code\nparameters: current equipment state
   * @param id Device unique identifier.
   * @return List<DeviceEquipment>
   */
  
  @GET("/device/{id}/equipment")
  List<DeviceEquipment> equipment(
          @Path("id") String id
  );

  /**
   * Get device&#39;s equipment
   * Async method
   * @param id Device unique identifier.
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{id}/equipment")
  void equipment(
          @Path("id") String id, Callback<List<DeviceEquipment>> cb
  );
  
  /**
   * Get current state of equipment
   * Sync method
   * Gets current state of device equipment by code.
   * @param id Device unique identifier.
   * @param code Equipment code.
   * @return DeviceEquipment
   */
  
  @GET("/device/{id}/equipment/{code}")
  DeviceEquipment equipmentByCode(
          @Path("id") String id, @Path("code") String code
  );

  /**
   * Get current state of equipment
   * Async method
   * @param id Device unique identifier.
   * @param code Equipment code.
   * @param cb callback method
   * @return void
   */
  
  @GET("/device/{id}/equipment/{code}")
  void equipmentByCode(
          @Path("id") String id, @Path("code") String code, Callback<DeviceEquipment> cb
  );
  
}
