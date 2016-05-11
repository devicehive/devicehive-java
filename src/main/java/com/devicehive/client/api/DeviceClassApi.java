package com.devicehive.client.api;


import com.devicehive.client.model.DeviceClass;
import com.devicehive.client.model.DeviceClassUpdate;
import com.devicehive.client.model.Equipment;
import com.devicehive.client.model.EquipmentUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceClassApi {
  /**
   * Update device class
   * Deletes an existing device class.
   * @param id Device class identifier. (required)
   * @return Call<Void>
   */
  
  @DELETE("device/class/{id}")
  Call<Void> deleteDeviceClass(
          @Path("id") Long id
  );

  /**
   * Delete equipment
   * Deletes equipment
   * @param deviceClassId Device class id (required)
   * @param id Equipment id (required)
   * @return Call<Void>
   */
  
  @DELETE("device/class/{deviceClassId}/equipment/{id}")
  Call<Void> deleteEquipment(
          @Path("deviceClassId") Long deviceClassId, @Path("id") Long id
  );

  /**
   * Get device class
   * Gets information about device class and its equipment.
   * @param id Device class identifier. (required)
   * @return Call<DeviceClass>
   */
  
  @GET("device/class/{id}")
  Call<DeviceClass> getDeviceClass(
          @Path("id") Long id
  );

  /**
   * List device classes
   * Gets list of device classes.
   * @param name Filter by device class name. (optional)
   * @param namePattern Filter by device class name pattern. (optional)
   * @param version Filter by device class version. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call<List<DeviceClass>>
   */
  
  @GET("device/class")
  Call<List<DeviceClass>> getDeviceClassList(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("version") String version, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Get equipment
   * Returns equipment by device class id and equipment id
   * @param deviceClassId Device class id (required)
   * @param id Equipment id (required)
   * @return Call<Void>
   */
  
  @GET("device/class/{deviceClassId}/equipment/{id}")
  Call<Equipment> getEquipment(
          @Path("deviceClassId") Long deviceClassId, @Path("id") Long id
  );

  /**
   * Create device class
   * Creates new device class.
   * @param body Device class body (required)
   * @return Call<DeviceClass>
   */
  
  @POST("device/class")
  Call<DeviceClass> insertDeviceClass(
          @Body DeviceClass body
  );

  /**
   * Create equipment
   * Creates equipment
   * @param deviceClassId Device class id (required)
   * @param body Equipment body (required)
   * @return Call<Void>
   */
  
  @POST("device/class/{deviceClassId}/equipment")
  Call<Void> insertEquipment(
          @Path("deviceClassId") Long deviceClassId, @Body Equipment body
  );

  /**
   * Update device class
   * Updates an existing device class.
   * @param id Device class identifier. (required)
   * @param body Device class body (required)
   * @return Call<Void>
   */
  
  @PUT("device/class/{id}")
  Call<Void> updateDeviceClass(
          @Path("id") Long id, @Body DeviceClassUpdate body
  );

  /**
   * Update equipment
   * Updates equipment
   * @param deviceClassId Device class id (required)
   * @param id Equipment id (required)
   * @param body Equipment body (required)
   * @return Call<Void>
   */
  
  @PUT("device/class/{deviceClassId}/equipment/{id}")
  Call<Void> updateEquipment(
          @Path("deviceClassId") Long deviceClassId, @Path("id") Long id, @Body EquipmentUpdate body
  );

}
