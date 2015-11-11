package com.devicehive.client.model;

import com.devicehive.client.impl.json.strategies.JsonPolicyDef;
import com.google.common.base.Optional;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents a device, a unit that runs microcode and communicates to this API.
 *
 * @see <a href="http://www.devicehive.com/restful#Reference/Device">Device</a>
 */
public class Device implements HiveEntity {

    private static final long serialVersionUID = -7498444232044147881L;
    @JsonPolicyDef({DEVICE_PUBLISHED, NETWORK_PUBLISHED, DEVICE_PUBLISHED_DEVICE_AUTH})
    private String id;

    @JsonPolicyDef({DEVICE_SUBMITTED, DEVICE_PUBLISHED})
    private Optional<String> key;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<String> name;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<String> status;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<String> data;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<Network> network;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<DeviceClass> deviceClass;


    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return NullableWrapper.value(key);
    }

    public void setKey(String key) {
        this.key = Optional.fromNullable(key);
    }

    public void removeKey() {
        this.key = null;
    }

    public String getName() {
        return NullableWrapper.value(name);
    }

    public void setName(String name) {
        this.name = Optional.fromNullable(name);
    }

    public void removeName() {
        this.name = null;
    }

    public String getStatus() {
        return NullableWrapper.value(status);
    }

    public void setStatus(String status) {
        this.status = Optional.fromNullable(status);
    }

    public void removeStatus() {
        this.status = null;
    }

    public String getData() {
        return NullableWrapper.value(data);
    }

    public void setData(String data) {
        this.data = Optional.fromNullable(data);
    }

    public void removeData() {
        this.data = null;
    }

    public Network getNetwork() {
        return NullableWrapper.value(network);
    }

    public void setNetwork(Network network) {
        this.network = Optional.fromNullable(network);
    }

    public void removeNetwork() {
        this.network = null;
    }

    public DeviceClass getDeviceClass() {
        return NullableWrapper.value(deviceClass);
    }

    public void setDeviceClass(DeviceClass deviceClass) {
        this.deviceClass = Optional.fromNullable(deviceClass);
    }

    public void removeDeviceClass() {
        this.deviceClass = null;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Device{");
        sb.append("name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", data=").append(data);
        sb.append(", network=").append(network);
        sb.append(", deviceClass=").append(deviceClass);
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
