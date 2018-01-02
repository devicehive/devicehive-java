/*
 *
 *
 *   NetworkService.java
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

package com.github.devicehive.client.service;

import com.github.devicehive.client.model.DHResponse;
import com.github.devicehive.client.model.NetworkFilter;
import com.github.devicehive.rest.api.NetworkApi;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.rest.model.NetworkUpdate;
import com.github.devicehive.rest.model.NetworkVO;

import java.util.List;

class NetworkService extends BaseService {

    DHResponse<List<Network>> listNetworks(NetworkFilter filter) {
        NetworkApi api = createService(NetworkApi.class);
        DHResponse<List<Network>> dhResponse;


        DHResponse<List<com.github.devicehive.rest.model.Network>> restResponse = execute(api.list(filter.name(),
                filter.namePattern(), filter.sortField(), filter.sortOrder(), filter.take(), filter.skip()));

        dhResponse = new DHResponse<List<Network>>(Network.createList(restResponse.getData()), restResponse.getFailureData());
        if (dhResponse.isSuccessful()) {
            return dhResponse;
        } else if (dhResponse.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            api = createService(NetworkApi.class);
            restResponse = execute(api.list(filter.name(),
                    filter.namePattern(), filter.sortField(), filter.sortOrder(), filter.take(), filter.skip()));
            dhResponse = new DHResponse<List<Network>>(Network.createList(restResponse.getData()), restResponse.getFailureData());
            return dhResponse;
        } else {
            return dhResponse;
        }
    }

    DHResponse<NetworkVO> getNetwork(long id) {
        NetworkApi api = createService(NetworkApi.class);
        DHResponse<NetworkVO> response = execute(api.get(id));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            api = createService(NetworkApi.class);
            return execute(api.get(id));
        } else {
            return response;
        }
    }

    DHResponse<Void> removeNetwork(long id) {
        NetworkApi api = createService(NetworkApi.class);
        DHResponse<Void> response = execute(api.delete(id));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            api = createService(NetworkApi.class);
            return execute(api.delete(id));
        } else {
            return response;
        }
    }

    DHResponse<Network> createNetwork(String name, String description) {

        NetworkUpdate network = new NetworkUpdate();
        network.setName(name);
        network.setDescription(description);

        NetworkApi api = createService(NetworkApi.class);

        DHResponse<Network> dhResponse;
        DHResponse<NetworkId> restResponse = execute(api.insert(network));

        dhResponse = createNetwork(name, description, restResponse);
        if (dhResponse.isSuccessful()) {
            return dhResponse;
        } else if (dhResponse.getFailureData().getCode() == 401) {
            refreshAndAuthorize();
            api = createService(NetworkApi.class);
            restResponse = execute(api.insert(network));
            dhResponse = createNetwork(name, description, restResponse);
            return dhResponse;
        } else {
            return dhResponse;
        }
    }

    private NetworkVO createCreateBody(String name, String description) {
        NetworkVO network = new NetworkVO();
        network.setName(name);
        network.setDescription(description);
        return network;
    }


    private DHResponse<Network> createNetwork(String name, String description, DHResponse<NetworkId> restResponse) {
        DHResponse<Network> dhResponse;
        if (restResponse.isSuccessful()) {
            dhResponse = new DHResponse<Network>(new Network.Builder()
                    .id(restResponse.getData().getId())
                    .name(name)
                    .description(description)
                    .build(),
                    null);
        } else {
            dhResponse = new DHResponse<Network>(null,
                    restResponse.getFailureData());
        }
        return dhResponse;
    }

    void updateNetwork(long id, String name, String description) {
        NetworkApi api = createService(NetworkApi.class);
        NetworkUpdate body = createUpdateBody(name, description);
        DHResponse<Void> restResponse = execute(api.update(id, body));
        if (!restResponse.isSuccessful()) {
            if (restResponse.getFailureData().getCode() == 401) {
                refreshAndAuthorize();
                api = createService(NetworkApi.class);
                execute(api.update(id, body));
            }
        }

    }

    private NetworkUpdate createUpdateBody(String name, String description) {
        NetworkUpdate network = new NetworkUpdate();
        network.setName(name);
        network.setDescription(description);
        return network;
    }
}
