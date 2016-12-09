package com.devicehive.client.api;

import com.devicehive.client.model.NetworkUpdate;
import com.devicehive.client.model.NetworkVO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface NetworkApi {
    /**
     * Delete network
     * Deletes an existing device network.
     *
     * @param id Network identifier. (required)
     * @return Call&lt;Void&gt;
     */

    @DELETE("network/{id}")
    Call<Void> delete(
            @Path("id") Long id
    );

    /**
     * Get network
     * Gets information about device network and its devices.
     *
     * @param id Network identifier. (required)
     * @return Call&lt;NetworkVO&gt;
     */

    @GET("network/{id}")
    Call<NetworkVO> get(
            @Path("id") Long id
    );

    /**
     * Create network
     * Creates new device network.
     *
     * @param body Network body (required)
     * @return Call&lt;NetworkVO&gt;
     */

    @POST("network")
    Call<NetworkVO> insert(
            @Body NetworkVO body
    );

    /**
     * List networks
     * Gets list of device networks the client has access to.
     *
     * @param name        Filter by network name. (optional)
     * @param namePattern Filter by network name pattern. (optional)
     * @param sortField   Result list sort field. (optional)
     * @param sortOrder   Result list sort order. (optional)
     * @param take        Number of records to take from the result list. (optional, default to 20)
     * @param skip        Number of records to skip from the result list. (optional, default to 0)
     * @return Call&lt;List<NetworkVO>&gt;
     */

    @GET("network")
    Call<List<NetworkVO>> list(
            @Query("name") String name, @Query("namePattern") String namePattern, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip
    );

    /**
     * Update network
     * Updates an existing device network.
     *
     * @param body Network body (required)
     * @param id   Network identifier. (required)
     * @return Call&lt;Void&gt;
     */

    @PUT("network/{id}")
    Call<Void> update(
            @Body NetworkUpdate body, @Path("id") Long id
    );


}
