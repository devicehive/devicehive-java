package com.devicehive.client2.api;


import retrofit2.Call;
import retrofit2.http.*;

public interface ConfigurationApi {
  /**
   * Delete property
   * Deletes property
   * @param name Property name (required)
   * @return Call<Void>
   */
  
  @DELETE("configuration/{name}")
  Call<Void> deleteProperty(
    @Path("name") String name
  );

  /**
   * Get property
   * Returns requested property value
   * @param name Property name (required)
   * @return Call<Void>
   */
  
  @GET("configuration/{name}")
  Call<Void> get(
    @Path("name") String name
  );

  /**
   * Create or update property
   * Creates new or updates existing property
   * @param name Property name (required)
   * @param body Property value (required)
   * @return Call<Void>
   */
  
  @PUT("configuration/{name}")
  Call<Void> setProperty(
    @Path("name") String name, @Body String body
  );

  /**
   * Create or update property
   * Creates new or updates existing property
   * @param name Property name (required)
   * @param value Property value (required)
   * @return Call<Void>
   */
  
  @GET("configuration/{name}/set")
  Call<Void> setPropertyGet(
    @Path("name") String name, @Query("value") String value
  );

}
