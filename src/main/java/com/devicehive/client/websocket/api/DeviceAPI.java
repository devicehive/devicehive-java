package com.devicehive.client.websocket.api;


import com.devicehive.client.websocket.model.Device;
import com.devicehive.client.websocket.model.DeviceClass;
import com.devicehive.client.websocket.model.exceptions.HiveException;

import java.util.List;

/**
 * The API for devices: {@code /device} and device classes: {@code device/class}.
 *
 * @see <a href="http://www.devicehive.com/restful#Reference/Device">DeviceHive RESTful API: Device</a>
 * @see <a href="http://www.devicehive.com/restful#Reference/DeviceClass">DeviceHive RESTful API: DeviceClass</a>
 */
public interface DeviceAPI {

    //
    // Device operations
    //

    /**
     * Queries a list of all available devices according to the specified parameters.
     *
     * @param name               Device name.
     * @param namePattern        Device name pattern.
     * @param status             Device status
     * @param networkId          Associated network identifier
     * @param networkName        Associated network name
     * @param deviceClassId      Associated device class identifier
     * @param deviceClassName    Associated device class name
     * @param deviceClassVersion Associated device class version
     * @param sortField          Result list sort field. Available values are "Name", "Status", "Network" and
     *                           "DeviceClass".
     * @param sortOrder          Result list sort order. Available values are ASC and DESC.
     * @param take               Number of records to take from the result list.
     * @param skip               Number of records to skip in the result list.
     * @return a list of {@link Device} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Device/list"> DeviceHive RESTful API: Device: list</a>
     */
    List<Device> listDevices(String name, String namePattern, String status, Long networkId, String networkName,
                             Integer deviceClassId, String deviceClassName, String deviceClassVersion,
                             String sortField, String sortOrder, Integer take, Integer skip) throws HiveException;

    /**
     * Retrieves information about device.
     *
     * @param deviceId Device unique identifier
     * @return If successful, this method returns a {@link Device} resource in the response body.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Device/get">DeviceHive RESTful API: Device: get</a>
     */
    Device getDevice(String deviceId) throws HiveException;

    /**
     * Registers a device. If device with specified identifier has already been registered, it's updated (providing a
     * valid key is specified in the authorization header).
     *
     * @param device   a {@link Device} resource to send in the request body
     * @param deviceId Device unique identifier.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Device/register">DeviceHive RESTful API: Device: register</a>
     */
    void registerDevice(String deviceId, Device device) throws HiveException;

    /**
     * Removes an existing device.
     *
     * @param deviceId Device unique identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/Device/delete">DeviceHive RESTful API: Device: delete</a>
     */
    void deleteDevice(String deviceId) throws HiveException;


    //
    // DeviceClass operations
    //

    /**
     * Retrieves a list of device classes filtered by the specified parameters.
     *
     * @param name        Device class name.
     * @param namePattern Device class name pattern.
     * @param version     Device class version.
     * @param sortField   Result list sort field. Available values are "ID" and "Name".
     * @param sortOrder   Result list sort order. Available values are ASC and DESC.
     * @param take        Number of records to take from the result list.
     * @param skip        Number of records to skip from the result list.
     * @return If successful, this method returns a list of {@link DeviceClass} resources in the response body.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceClass/list">DeviceHive RESTful API: DeviceClass:
     * list</a>
     */
    List<DeviceClass> listDeviceClass(String name, String namePattern, String version, String sortField,
                                      String sortOrder, Integer take, Integer skip) throws HiveException;

    /**
     * Retrieves information about a device class and equipment.
     *
     * @param classId device class identifier
     * @return If successful, this method returns a {@link DeviceClass} resource in the response body.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceClass/get">DeviceHive RESTful API: DeviceClass:
     * get</a>
     */
    DeviceClass getDeviceClass(long classId) throws HiveException;

    /**
     * Creates new device class.
     *
     * @param deviceClass device class to be inserted
     * @return a device class identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceClass/insert">DeviceHive RESTful API: DeviceClass:
     * insert</a>
     */
    long insertDeviceClass(DeviceClass deviceClass) throws HiveException;

    /**
     * Updates an existing device class.
     *
     * @param deviceClass a device class to be updated
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceClass/update">DeviceHive RESTful API: DeviceClass:
     * update</a>
     */
    void updateDeviceClass(DeviceClass deviceClass) throws HiveException;

    /**
     * Removes an existing device class by id.
     *
     * @param classId a device class identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceClass/delete">DeviceHive RESTful API: DeviceClass:
     * delete</a>
     */
    void deleteDeviceClass(long classId) throws HiveException;
}
