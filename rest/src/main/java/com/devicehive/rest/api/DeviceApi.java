package com.devicehive.rest.api;

import com.devicehive.rest.model.DeviceUpdate;
import com.devicehive.rest.model.DeviceVO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface DeviceApi {
    /**
     * Delete device
     * Deletes an existing device.
     *
     * @param id            Device unique identifier. (required)
     * @return Call&lt;Void&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @DELETE("device/{id}")
    Call<Void> delete(
            @Path("id") String id);

    /**
     * Get device
     * Gets information about device.
     *
     * @param id            Device unique identifier. (required)
     * @return Call&lt;Void&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device/{id}")
    Call<DeviceVO> get(
            @Path("id") String id
    );

    /**
     * List devices
     * Gets list of devices.
     *
     * @param name        Filter by device name. (optional)
     * @param namePattern Filter by device name pattern. In pattern wildcards &#39;%&#39; and &#39;_&#39; can be used. (optional)
     * @param networkId   Filter by associated network identifier. (optional)
     * @param networkName Filter by associated network name. (optional)
     * @param sortField   Result list sort field. (optional)
     * @param sortOrder   Result list sort order. The sortField should be specified. (optional)
     * @param take        Number of records to take from the result list. (optional, default to 20)
     * @param skip        Number of records to skip from the result list. (optional, default to 0)
     * @return Call&lt;List&lt;DeviceVO&gt;&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @GET("device")
    Call<List<DeviceVO>> list(
            @Query("name") String name,
            @Query("namePattern") String namePattern,
            @Query("networkId") Long networkId,
            @Query("networkName") String networkName,
            @Query("sortField") String sortField,
            @Query("sortOrder") String sortOrder,
            @Query("take") Integer take,
            @Query("skip") Integer skip
    );

    /**
     * Register device
     * Registers or updates a device. For initial device registration, only &#39;name&#39; property is required.
     *
     * @param body Device body (required)
     * @param id   Device unique identifier. (required)
     * @return Call&lt;Void&gt;
     */
    @Headers({
            "Content-Type:application/json"
    })
    @PUT("device/{id}")
    Call<Void> register(
            @Body DeviceUpdate body, @Path("id") String id
    );

}
