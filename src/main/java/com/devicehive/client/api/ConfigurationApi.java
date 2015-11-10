package com.devicehive.client.api;

import retrofit.Callback;
import retrofit.http.*;

public interface ConfigurationApi {

    /**
     * Get property
     * Sync method
     * Returns requested property value
     *
     * @param name Property name
     * @return Void
     */

    @GET("/configuration/{name}")
    Void get(
            @Path("name") String name
    );

    /**
     * Get property
     * Async method
     *
     * @param name Property name
     * @param cb   callback method
     * @return void
     */

    @GET("/configuration/{name}")
    void get(
            @Path("name") String name,
            Callback<Void> cb
    );

    /**
     * Create or update property
     * Sync method
     * Creates new or updates existing property
     *
     * @param name Property name
     * @param body Property value
     * @return Void
     */

    @PUT("/configuration/{name}")
    Void setProperty(
            @Path("name") String name,
            @retrofit.http.Body com.devicehive.client.model.Body body
    );

    /**
     * Create or update property
     * Async method
     *
     * @param name Property name
     * @param body Property value
     * @param cb   callback method
     * @return void
     */

    @PUT("/configuration/{name}")
    void setProperty(
            @Path("name") String name,
            @retrofit.http.Body com.devicehive.client.model.Body body,
            Callback<Void> cb
    );

    /**
     * Delete property
     * Sync method
     * Deletes property
     *
     * @param name Property name
     * @return Void
     */

    @DELETE("/configuration/{name}")
    Void deleteProperty(
            @Path("name") String name
    );

    /**
     * Delete property
     * Async method
     *
     * @param name Property name
     * @param cb   callback method
     * @return void
     */

    @DELETE("/configuration/{name}")
    void deleteProperty(
            @Path("name") String name,
            Callback<Void> cb
    );

    /**
     * Create or update property
     * Sync method
     * Creates new or updates existing property
     *
     * @param name  Property name
     * @param value Property value
     * @return Void
     */

    @GET("/configuration/{name}/set")
    Void setPropertyGet(
            @Path("name") String name,
            @Query("value") String value
    );

    /**
     * Create or update property
     * Async method
     *
     * @param name  Property name
     * @param value Property value
     * @param cb    callback method
     * @return void
     */

    @GET("/configuration/{name}/set")
    void setPropertyGet(
            @Path("name") String name,
            @Query("value") String value,
            Callback<Void> cb
    );

}
