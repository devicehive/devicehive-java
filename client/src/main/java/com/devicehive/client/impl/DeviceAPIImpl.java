package com.devicehive.client.impl;


import com.google.common.reflect.TypeToken;

import com.devicehive.client.DeviceAPI;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.model.Device;
import com.devicehive.client.model.DeviceClass;
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

import javax.ws.rs.HttpMethod;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.DEVICECLASS_LISTED;
import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.DEVICECLASS_PUBLISHED;
import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.DEVICECLASS_SUBMITTED;
import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.DEVICE_PUBLISHED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link DeviceController}.
 */
class DeviceAPIImpl implements DeviceAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceAPIImpl.class);

    private static final String DEVICES_COLLECTION_PATH = "/device";
    private static final String DEVICE_RESOURCE_PATH = "/device/%s";
    private static final String DEVICE_EQUIPMENT_RESOURCE_PATH = "/device/%s/equipment";
    private static final String DEVICE_CLASSES_COLLECTION_PATH = "/device/class";
    private static final String DEVICE_CLASS_RESOURCE_PATH = "/device/class/%s";

    private final RestAgent restAgent;

    /**
     * Initializes the controller with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    DeviceAPIImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    //
    // Device operations
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Device> listDevices(String name, String namePattern, String status, Long networkId,
                                    String networkName, Integer deviceClassId, String deviceClassName,
                                    String deviceClassVersion, String sortField, String sortOrder, Integer take,
                                    Integer skip) throws HiveException {
        LOGGER.debug("Device: list requested with following parameters: name {}, name pattern {}, network id {}, " +
            "network name {}, device class id {}, device class name {}, device class version {}, sort field {}, " +
            "sort order {}, take {}, skip {}", name, namePattern, networkId, networkName, deviceClassId,
            deviceClassName, deviceClassVersion, sortField, sortOrder, take, skip);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", name);
        queryParams.put("namePattern", namePattern);
        queryParams.put("status", status);
        queryParams.put("networkId", networkId);
        queryParams.put("networkName", networkName);
        queryParams.put("deviceClassId", deviceClassId);
        queryParams.put("deviceClassName", deviceClassName);
        queryParams.put("deviceClassVersion", deviceClassVersion);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        String path = DEVICES_COLLECTION_PATH;
        Type type = new TypeToken<List<Device>>() {}.getType();

        List<Device> result = restAgent.execute(path, HttpMethod.GET, null, queryParams, type, DEVICE_PUBLISHED);

        LOGGER.debug("Device: list request proceed successfully for following parameters: name {}, name pattern {}, " +
            "network id {}, network name {}, device class id {}, device class name {}, device class version {}, " +
            "sort field {}, sort order {}, take {}, skip {}", name, namePattern, networkId, networkName, deviceClassId,
            deviceClassName, deviceClassVersion, sortField, sortOrder, take, skip);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device getDevice(String deviceId) throws HiveException {
        LOGGER.debug("Device: get requested for device id {}", deviceId);

        String path = String.format(DEVICE_RESOURCE_PATH, deviceId);
        Device result = restAgent.execute(path, HttpMethod.GET, null, Device.class, DEVICE_PUBLISHED);

        LOGGER.debug("Device: get request proceed successfully for device id {}. Device name {}, status {}, data {}, " +
            "network id {}, network name {}, device class id {}, device class name {}, device class verison {}",
            deviceId, result.getName(), result.getStatus(), result.getData(),
            result.getNetwork() != null ? result.getNetwork().getId() : null,
            result.getNetwork() != null ? result.getNetwork().getName() : null,
            result.getDeviceClass().getId(), result.getDeviceClass().getName(), result.getDeviceClass().getVersion());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDevice(String deviceId, Device device) throws HiveException {
        if (device == null) {
            throw new HiveClientException("Device cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("Device: register requested for device id {} Device name {}, status {}, data {}, network id {}, " +
            "network name {}, device class id {}, device class name {}, device class verison {}",
            deviceId, device.getName(), device.getStatus(), device.getData(),
            device.getNetwork() != null ? device.getNetwork().getId() : null,
            device.getNetwork() != null ? device.getNetwork().getName() : null,
            device.getDeviceClass() != null ? device.getDeviceClass().getId() : null,
            device.getDeviceClass() != null ? device.getDeviceClass().getName() : null,
            device.getDeviceClass() != null ? device.getDeviceClass().getVersion() : null);

        String path = String.format(DEVICE_RESOURCE_PATH, deviceId);
        restAgent.execute(path, HttpMethod.PUT, null, device, DEVICE_PUBLISHED);

        LOGGER.debug("Device: register request proceed successfully for device id {} Device name {}, status {}, data {}, " +
            "network id {}, network name {}, device class id {}, device class name {}, device class verison {}",
            deviceId, device.getName(), device.getStatus(), device.getData(),
            device.getNetwork() != null ? device.getNetwork().getId() : null,
            device.getNetwork() != null ? device.getNetwork().getName() : null,
            device.getDeviceClass() != null ? device.getDeviceClass().getId() : null,
            device.getDeviceClass() != null ? device.getDeviceClass().getName() : null,
            device.getDeviceClass() != null ? device.getDeviceClass().getVersion() : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDevice(String deviceId) throws HiveException {
        LOGGER.debug("Device: delete requested for device with id {}", deviceId);

        String path = String.format(DEVICE_RESOURCE_PATH, deviceId);
        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("Device: delete request proceed successfully for device with id {}", deviceId);
    }


    //
    // Device Classes operations
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceClass> listDeviceClass(String name, String namePattern, String version, String sortField,
                                             String sortOrder, Integer take, Integer skip) throws HiveException {
        LOGGER.debug("DeviceClass: list requested with parameters: name {}, name pattern {}, version {}, sort field {}, " +
            "sort order {}, take param {}, skip  param {}", name, namePattern, version, sortField, sortOrder, take, skip);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", name);
        queryParams.put("namePattern", namePattern);
        queryParams.put("version", version);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);

        String path = DEVICE_CLASSES_COLLECTION_PATH;
        Type type = new TypeToken<List<DeviceClass>>() {}.getType();

        List<DeviceClass> result = restAgent.execute(path, HttpMethod.GET, null, queryParams, type, DEVICECLASS_LISTED);

        LOGGER.debug("DeviceClass: list request proceed for following params: name {}, name pattern {}, version {}, " +
            "sort field {}, sort order {}, take param {}, skip  param {}", name, namePattern, version, sortField, sortOrder,
            take, skip);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceClass getDeviceClass(long classId) throws HiveException {
        LOGGER.debug("DeviceClass: get requested for class with id {}", classId);

        String path = String.format(DEVICE_CLASS_RESOURCE_PATH, classId);
        DeviceClass result = restAgent.execute(path, HttpMethod.GET, null, DeviceClass.class, DEVICECLASS_PUBLISHED);

        LOGGER.debug("DeviceClass: get request proceed for class with id {}", classId);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long insertDeviceClass(DeviceClass deviceClass) throws HiveException {
        if (deviceClass == null) {
            throw new HiveClientException("Device class cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceClass: insert requested for device class with name {}, version {}", deviceClass.getName(),
            deviceClass.getVersion());

        String path = DEVICE_CLASSES_COLLECTION_PATH;
        DeviceClass inserted = restAgent.execute(path, HttpMethod.POST, null, null, deviceClass, DeviceClass.class,
            DEVICECLASS_PUBLISHED, DEVICECLASS_SUBMITTED);

        LOGGER.debug("DeviceClass: insert request proceed for device class with name {}, version {}. Result id {}",
            deviceClass.getName(), deviceClass.getVersion(), inserted.getId());

        return inserted.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDeviceClass(DeviceClass deviceClass) throws HiveException {
        if (deviceClass == null) {
            throw new HiveClientException("Device class cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (deviceClass.getId() == null) {
            throw new HiveClientException("Device class id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceClass: update requested for device class with id {}, name {}, version {}",
            deviceClass.getId(), deviceClass.getName(), deviceClass.getVersion());

        String path = String.format(DEVICE_CLASS_RESOURCE_PATH, deviceClass.getId());
        restAgent.execute(path, HttpMethod.PUT, null, deviceClass, DEVICECLASS_PUBLISHED);

        LOGGER.debug("DeviceClass: update request proceed for device class with id {}, name {}, version {}",
            deviceClass.getId(), deviceClass.getName(), deviceClass.getVersion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDeviceClass(long classId) throws HiveException {
        LOGGER.debug("DeviceClass: delete requested for class with id {}", classId);

        String path = String.format(DEVICE_CLASS_RESOURCE_PATH, classId);

        restAgent.execute(path, HttpMethod.DELETE);

        LOGGER.debug("DeviceClass: delete request proceed for class with id {}", classId);
    }
}
