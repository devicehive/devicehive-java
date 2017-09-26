package com.devicehive.client.service;

import com.devicehive.client.model.DHResponse;
import com.devicehive.client.model.NetworkFilter;
import com.devicehive.rest.api.NetworkApi;
import com.devicehive.rest.model.Network;
import com.devicehive.rest.model.NetworkId;
import com.devicehive.rest.model.NetworkVO;

import java.io.IOException;
import java.util.List;

public class NetworkService extends BaseService {

    public DHResponse<List<Network>> listNetworks(NetworkFilter filter) throws IOException {
        NetworkApi api = createService(NetworkApi.class);
        DHResponse<List<Network>> response = execute(api.list(filter.name(),
                filter.namePattern(), filter.sortField(), filter.sortOrder(), filter.take(), filter.skip()));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            api = createService(NetworkApi.class);
            return execute(api.list(filter.name(),
                    filter.namePattern(), filter.sortField(), filter.sortOrder(), filter.take(), filter.skip()));
        } else {
            return response;
        }
    }

    public DHResponse<NetworkVO> getNetwork(long id) throws IOException {
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

    public DHResponse<Void> removeNetwork(long id) throws IOException {
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

    public DHResponse<NetworkId> createNetwork(String name, String description) throws IOException {
        NetworkVO network = new NetworkVO();
        network.setName(name);
        network.setDescription(description);


        NetworkApi api = createService(NetworkApi.class);
        DHResponse<NetworkId> response = execute(api.insert(network));
        if (response.isSuccessful()) {
            return response;
        } else if (response.getFailureData().getCode() == 401) {
            authorize();
            api = createService(NetworkApi.class);
            return execute(api.insert(network));
        } else {
            return response;
        }
    }
}
