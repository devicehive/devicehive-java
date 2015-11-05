package com.devicehive.client;


import com.devicehive.client.model.Network;
import com.devicehive.client.model.exceptions.HiveException;

import java.util.List;

/**
 * Client side controller for network: {@code /network}. Transport declared in the hive context will be used.
 *
 * @see <a href="http://www.devicehive.com/restful/#Reference/Network">DeviceHive RESTful API: Network</a>
 */
public interface NetworkAPI {

    /**
     * Queries list of networks using the following criteria.
     *
     * @param name        exact network's name, ignored, when  namePattern is not null
     * @param namePattern name pattern
     * @param sortField   sort Field, can be either "id", "key", "name" or "description"
     * @param sortOrder   Result list sort order. Available values are ASC and DESC.
     * @param take        limit, default 1000
     * @param skip        offset, default 0
     * @return a list of {@link Network} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Network/list">DeviceHive RESTful API: Network: list</a>
     */
    List<Network> listNetworks(String name, String namePattern, String sortField, String sortOrder, Integer take,
                               Integer skip) throws HiveException;

    /**
     * Retrieves information about a network.
     *
     * @param id a network identifier
     * @return If successful, this method returns a {@link Network} resource in the response body.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Network/get">DeviceHive RESTful API: Network: get</a>
     */
    Network getNetwork(long id) throws HiveException;

    /**
     * Creates a new network.
     *
     * @param network a network to be inserted
     * @return a network identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Network/insert">DeviceHive RESTful API: Network: insert</a>
     */
    long insertNetwork(Network network) throws HiveException;

    /**
     * Updates an existing network.
     *
     * @param network a network to be updated
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Network/update">DeviceHive RESTful API: Network: update</a>
     */
    void updateNetwork(Network network) throws HiveException;

    /**
     * Removes a network by its identifier.
     *
     * @param id a network identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Network/delete">DeviceHive RESTful API: Network: delete</a>
     */
    void deleteNetwork(long id) throws HiveException;
}
