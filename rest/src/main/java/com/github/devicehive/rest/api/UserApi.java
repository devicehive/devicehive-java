/*
 *
 *
 *   UserApi.java
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

import com.github.devicehive.rest.model.UserInsert;
import com.github.devicehive.rest.model.UserNetworkResponse;
import com.github.devicehive.rest.model.UserUpdate;
import com.github.devicehive.rest.model.UserVO;
import com.github.devicehive.rest.model.UserWithNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserApi {
  /**
   * Assign network
   * Associates network with the user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("user/{id}/network/{networkId}")
  Call<Void> assignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Delete user
   * Deletes an existing user.
   * @param id User identifier. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("user/{id}")
  Call<Void> deleteUser(
          @Path("id") Long id
  );

  /**
   * Get current user
   * Get information about the current user.
   * @return Call&lt;UserWithNetwork&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user/current")
  Call<UserWithNetwork> getCurrent();

  /**
   * Get user&#39;s network
   * Gets information about user/network association.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call&lt;UserNetworkResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user/{id}/network/{networkId}")
  Call<UserNetworkResponse> getNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Get user
   * Gets information about user and its assigned networks. Only administrators are allowed to get information about any user. User-level accounts can only retrieve information about themselves.
   * @param id User identifier. (required)
   * @return Call&lt;UserResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user/{id}")
  Call<com.github.devicehive.rest.model.UserResponse> getUser(
          @Path("id") Long id
  );

  /**
   * Create user
   * Creates new user.
   * @param body User body (required)
   * @return Call&lt;UserVO&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("user")
  Call<UserInsert> insertUser(
          @Body UserUpdate body
  );

  /**
   * List users
   * Gets list of users.
   * @param login Filter by user login. (optional)
   * @param loginPattern Filter by user login pattern. (optional)
   * @param role Filter by user role. 0 is Administrator, 1 is Client. (optional)
   * @param status Filter by user status. 0 is Active, 1 is Locked Out, 2 is Disabled. (optional)
   * @param sortField Result list sort field. (optional)
   * @param sortOrder Result list sort order. The sortField should be specified. (optional)
   * @param take Number of records to take from the result list. (optional, default to 20)
   * @param skip Number of records to skip from the result list. (optional, default to 0)
   * @return Call&lt;List&lt;UserVO&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("user")
  Call<List<UserVO>> list(
         @Query("login") String login, @Query("loginPattern") String loginPattern, @Query("role") Integer role,
         @Query("status") Integer status, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder,
         @Query("take") Integer take, @Query("skip") Integer skip
  );

  /**
   * Unassign network
   * Removes association between network and user.
   * @param id User identifier. (required)
   * @param networkId Network identifier. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @DELETE("user/{id}/network/{networkId}")
  Call<Void> unassignNetwork(
          @Path("id") Long id, @Path("networkId") Long networkId
  );

  /**
   * Update current user
   * Updates an existing user.  Only administrators are allowed to update any property of any user. User-level accounts can only change their own password in case: 1. They already have a password. 2. They provide a valid current password in the &#39;oldPassword&#39; property.
   * @param body User body (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("user/current")
  Call<Void> updateCurrentUser(
          @Body UserUpdate body
  );

  /**
   * Update user
   *
   * @param body User body (required)
   * @param id User identifier. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("user/{id}")
  Call<Void> updateUser(
          @Path("id") Long id, @Body UserUpdate body
  );

}
