package com.devicehive.client.api;


import com.devicehive.client.model.DeviceClassEquipmentVO;
import com.devicehive.client.model.DeviceClassUpdate;
import com.devicehive.client.model.DeviceClassWithEquipmentVO;
import com.devicehive.client.model.EquipmentUpdate;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DeviceClassApi {
  /**
   * Update device class
   * Deletes an existing device class.
   * @param id Device class identifier. (required)
   * @return Call&lt;Void&gt;
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
   * @return Call&lt;Void&gt;
   */

  @DELETE("device/class/{deviceClassId}/equipment/{id}")
  Call<Void> deleteEquipment(
          @Path("deviceClassId") Long deviceClassId, @Path("id") Long id
  );

  /**
   * Get device class
   * Gets information about device class and its equipment.
   * @param id Device class identifier. (required)
   * @return Call&lt;DeviceClassWithEquipmentVO&gt;
   */

  @GET("device/class/{id}")
  Call<DeviceClassWithEquipmentVO> getDeviceClass(
          @Path("id") Long id
  );

  /**
   * List device classes
   * Gets list of device classes.
   * @param name Filter by device class name. (optional)
   * @param namePattern Filter by device class name pattern. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call&lt;List<DeviceClassWithEquipmentVO>&gt;
   */

  @GET("device/class")
  Call<List<DeviceClassWithEquipmentVO>> getDeviceClassList(
          @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Get equipment
   * Returns equipment by device class id and equipment id
   * @param deviceClassId Device class id (required)
   * @param id Equipment id (required)
   * @return Call&lt;DeviceClassEquipmentVO&gt;
   */

  @GET("device/class/{deviceClassId}/equipment/{id}")
  Call<DeviceClassEquipmentVO> getEquipment(
          @Path("deviceClassId") Long deviceClassId, @Path("id") Long id
  );

  /**
   * Create device class
   * Creates new device class.
   * @param body Device class body (required)
   * @return Call&lt;DeviceClassWithEquipmentVO&gt;
   */

  @POST("device/class")
  Call<DeviceClassWithEquipmentVO> insertDeviceClass(
          @Body DeviceClassWithEquipmentVO body
  );

  /**
   * Create equipment
   * Creates equipment
   * @param deviceClassId Device class {id} (required)
   * @param body Equipment body (required)
   * @return Call&lt;DeviceClassEquipmentVO&gt;
   */

  @POST("device/class/{deviceClassId}/equipment")
  Call<DeviceClassEquipmentVO> insertEquipment(
          @Path("deviceClassId") Long deviceClassId, @Body DeviceClassEquipmentVO body
  );

  /**
   * Update device class
   * Updates an existing device class.
   * @param id Device class identifier. (required)
   * @param body Device class body (required)
   * @return Call&lt;Void&gt;
   */

  @PUT("device/class/{id}")
  Call<Void> updateDeviceClass(
          @Path("id") Long id, @Body DeviceClassUpdate body
  );

  /**
   * Update equipment
   * Updates equipment. Returns empty body if equipment updated.
   * @param deviceClassId Device class id (required)
   * @param id Equipment id (required)
   * @param body Equipment body (required)
   * @return Call&lt;Void&gt;
   */

  @PUT("device/class/{deviceClassId}/equipment/{id}")
  Call<Void> updateEquipment(
          @Path("deviceClassId") Long deviceClassId, @Path("id") Long id, @Body EquipmentUpdate body
  );

}
