package com.devicehive.client.model;

import com.devicehive.client.impl.json.strategies.JsonPolicyDef;
import com.google.common.base.Optional;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents a device class which holds meta-information about devices. For more details see <a
 * href="http://www.devicehive.com/restful#Reference/DeviceClass">DeviceClass</a>
 */
public class DeviceClass implements HiveEntity {

    private static final long serialVersionUID = 967472386318199376L;
    @JsonPolicyDef(
        {DEVICE_PUBLISHED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED, DEVICECLASS_SUBMITTED,
         DEVICE_PUBLISHED_DEVICE_AUTH})
    private Long id;


    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED,
                    DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<String> name;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED,
                    DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<String> version;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED,
                    DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<Boolean> isPermanent;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED,
                    DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<Integer> offlineTimeout;

    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED,
                    DEVICE_PUBLISHED_DEVICE_AUTH})
    private Optional<String> data;

    public DeviceClass() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getVersion() {
        return NullableWrapper.value(version);
    }

    public void setVersion(String version) {
        this.version = Optional.fromNullable(version);
    }

    public void removeVersion() {
        this.version = null;
    }

    public Boolean getPermanent() {
        return NullableWrapper.value(isPermanent);
    }

    public void setPermanent(Boolean permanent) {
        isPermanent = Optional.fromNullable(permanent);
    }

    public void removePermanent() {
        this.isPermanent = null;
    }

    public Integer getOfflineTimeout() {
        return NullableWrapper.value(offlineTimeout);
    }

    public void setOfflineTimeout(Integer offlineTimeout) {
        this.offlineTimeout = Optional.fromNullable(offlineTimeout);
    }

    public void removeOfflineTimeout() {
        this.offlineTimeout = null;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceClass{");
        sb.append("data=").append(data);
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", version=").append(version);
        sb.append(", isPermanent=").append(isPermanent);
        sb.append(", offlineTimeout=").append(offlineTimeout);
        sb.append('}');
        return sb.toString();
    }
}
