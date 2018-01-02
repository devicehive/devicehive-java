/*
 *
 *
 *   ConfigurationApi.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.rest.api;

import com.github.devicehive.rest.model.Configuration;
import com.github.devicehive.rest.model.ValueProperty;
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
