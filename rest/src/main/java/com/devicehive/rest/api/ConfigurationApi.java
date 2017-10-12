package com.devicehive.rest.api;

import com.devicehive.rest.model.Configuration;
import com.devicehive.rest.model.ValueProperty;
import retrofit2.Call;
import retrofit2.http.*;


public interface ConfigurationApi {
  /**
   * Delete property
   * Deletes property
   * @param name Property name (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("configuration/{name}")
  Call<Void> deleteProperty(
          @Path("name") String name
  );

  /**
   * Get property
   * Returns requested property value
   * @param name Property name (required)
   * @return Call&lt;Configuration&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("configuration/{name}")
  Call<Configuration> get(
          @Path("name") String name
  );

  /**
   * Create or update property
   * Creates new or updates existing property
   * @param name Property name (required)
   * @param body Property value (required)
   * @return Call&lt;Configuration&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("configuration/{name}")
  Call<Configuration> setProperty(
          @Path("name") String name, @Body ValueProperty body
  );

}
