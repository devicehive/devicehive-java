package com.devicehive.client.impl;


import com.google.common.reflect.TypeToken;

import com.devicehive.client.NetworkAPI;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.model.Network;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link NetworkController}.
 */
class NetworkAPIImpl implements NetworkAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkAPIImpl.class);

    private static final String NETWORKS_COLLECTION_PATH = "/network";
    private static final String NETWORK_RESOURCE_PATH = "/network/%s";

    private final RestAgent restAgent;

    /**
     * Initializes the controller with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    NetworkAPIImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Network> listNetworks(String name, String namePattern, String sortField, String sortOrder, Integer take,
                                      Integer skip) throws HiveException {
        LOGGER.debug("Network: list requested with parameters: name {}, name pattern {}, sort field {}, sort order {}, " +
            "take {}, skip {}", name, namePattern, sortField, sortOrder, take, skip);

        String path = NETWORKS_COLLECTION_PATH;
        Type type = new TypeToken<List<Network>>() {}.getType();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", name);
        queryParams.put("namePattern", namePattern);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        List<Network> result = restAgent.execute(path, HttpMethod.GET, null, queryParams, type, NETWORKS_LISTED);

        LOGGER.debug("Network: list request proceed with parameters: name {}, name pattern {}, sort field {}, " +
            "sort order {}, take {}, skip {}", name, namePattern, sortField, sortOrder, take, skip);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Network getNetwork(long id) throws HiveException {
        LOGGER.debug("Network: get requested for network with id {}", id);

        String path = String.format(NETWORK_RESOURCE_PATH, id);
        Network result = restAgent.execute(path, HttpMethod.GET, null, Network.class, NETWORK_PUBLISHED);

        LOGGER.debug("Network: get requested for network with id {}. Network name {}", id, result.getName());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long insertNetwork(Network network) throws HiveException {
        if (network == null) {
            throw new HiveClientException("Network cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("Network: insert requested for network with name {}", network.getName());

        String path = NETWORKS_COLLECTION_PATH;
        Network returned = restAgent.execute(path, HttpMethod.POST, null, null, network, Network.class, NETWORK_UPDATE,
            NETWORK_SUBMITTED);

        LOGGER.debug("Network: insert request proceed for network with name {}. Result id {}", network.getName(),
                     returned.getId());

        return returned.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateNetwork(Network network) throws HiveException {
        if (network == null) {
            throw new HiveClientException("Network cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (network.getId() == null) {
            throw new HiveClientException("Network id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("Network: update requested for network with name {} and id {}", network.getName(), network.getId());

        String path = String.format(NETWORK_RESOURCE_PATH, network.getId());
        restAgent.execute(path, HttpMethod.PUT, null, network, NETWORK_UPDATE);

        LOGGER.debug("Network: update request proceed for network with name {} and id {}", network.getName(),
            network.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNetwork(long id) throws HiveException {
        LOGGER.debug("Network: delete requested for network with id {}", id);

        String path = String.format(NETWORK_RESOURCE_PATH, id);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("Network: delete request proceed for network with id {}", id);
    }
}
