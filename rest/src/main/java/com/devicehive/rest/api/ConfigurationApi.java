package com.devicehive.rest.api;

import com.devicehive.rest.model.ConfigurationVO;
import retrofit2.Call;
import retrofit2.http.*;


public interface ConfigurationApi {
  /**
   * Delete property
   * Deletes property
   * @param name Property name (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("configuration/{name}")
  Call<Void> deleteProperty(
          @Path("name") String name, @Header("Authorization") String authorization
  );

  /**
   * Get property
   * Returns requested property value
   * @param name Property name (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;ConfigurationVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("configuration/{name}")
  Call<ConfigurationVO> get(
          @Path("name") String name, @Header("Authorization") String authorization
  );

  /**
   * Create or update property
   * Creates new or updates existing property
   * @param name Property name (required)
   * @param body Property value (required)
   * @param authorization Authorization token (required)
   * @return Call&lt;ConfigurationVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("configuration/{name}")
  Call<ConfigurationVO> setProperty(
          @Path("name") String name, @Body String body, @Header("Authorization") String authorization
  );

}
