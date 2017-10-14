package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.NetworkFilter;
import com.devicehive.rest.api.NetworkApi;
import com.devicehive.rest.model.NetworkId;
import com.devicehive.rest.model.NetworkUpdate;
import com.devicehive.rest.model.NetworkVO;

import java.util.List;

class NetworkService extends BaseService {

    DHResponse<List<Network>> listNetworks(NetworkFilter filter) {
        NetworkApi api = createService(NetworkApi.class);
        DHResponse<List<Network>> dhResponse;


        DHResponse<List<com.devicehive.rest.model.Network>> restResponse = execute(api.list(filter.name(),
                filter.namePattern(), filter.sortField(), filter.sortOrder(), filter.take(), filter.skip()));

        dhResponse = new DHResponse<List<Network>>(Network.createList(restResponse.getData()), restResponse.getFailureData());
        if (dhResponse.isSuccessful()) {
            return dhResponse;
        } else if (dhResponse.getFailureData().getCode() == 401) {
            authorize();
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
            authorize();
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
            authorize();
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
            authorize();
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
            dhResponse = new DHResponse<Network>(Network.builder()
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
        System.out.println(body);
        DHResponse<Void> restResponse = execute(api.update(id, body));
        if (!restResponse.isSuccessful()) {
            if (restResponse.getFailureData().getCode() == 401) {
                authorize();
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
