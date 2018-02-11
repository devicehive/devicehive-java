/*
 *
 *
 *   DeviceTypeApi.java
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

import com.github.devicehive.rest.model.DeviceType;
import com.github.devicehive.rest.model.DeviceTypeCount;
import com.github.devicehive.rest.model.DeviceTypeInserted;
import com.github.devicehive.rest.model.DeviceTypeListItem;
import com.github.devicehive.rest.model.DeviceTypeUpdate;

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

public interface DeviceTypeApi {

    @Headers({
            "Content-Type:application/json"
    })
    @GET("devicetype")
    Call<List<DeviceTypeListItem>> list(
            @Query("name") String name,
            @Query("namePattern") String namePattern,
            @Query("sortField") String sortField,
            @Query("sortOrder") String sortOrder,
            @Query("take") Integer take,
            @Query("skip") Integer skip
    );

    @Headers({
            "Content-Type:application/json"
    })
    @GET("devicetype/{id}")
    Call<DeviceType> get(
            @Path("id") long id
    );

    @Headers({
            "Content-Type:application/json"
    })
    @GET("devicetype/count")
    Call<DeviceTypeCount> count(
            @Query("name") String name,
            @Query("namePattern") String namePattern
    );

    @Headers({
            "Content-Type:application/json"
    })
    @POST("devicetype")
    Call<DeviceTypeInserted> insert(
            @Body DeviceTypeUpdate body
    );

    @Headers({
            "Content-Type:application/json"
    })
    @PUT("devicetype/{id}")
    Call<Void> update(
            @Body DeviceTypeUpdate body, @Path("id") long id
    );

    @Headers({
            "Content-Type:application/json"
    })
    @DELETE("devicetype/{id}")
    Call<Void> delete(
            @Path("id") long id);


}
