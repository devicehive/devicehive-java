package com.devicehive.model;


import com.devicehive.json.strategies.JsonPolicyDef;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static com.devicehive.json.strategies.JsonPolicyDef.Policy.NOTIFICATION_FROM_DEVICE;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.NOTIFICATION_TO_CLIENT;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.NOTIFICATION_TO_DEVICE;
import static com.devicehive.model.DeviceNotification.Queries.Names;
import static com.devicehive.model.DeviceNotification.Queries.Values;

/**
 * TODO JavaDoc
 */
@Entity
@Table(name = "device_notification")
@NamedQueries(value = {
        @NamedQuery(name = Names.DELETE_BY_ID, query = Values.DELETE_BY_FK),
        @NamedQuery(name = Names.DELETE_BY_FK, query = Values.DELETE_BY_FK)

})
@Cacheable
public class DeviceNotification implements HiveEntity {
    public static final String TIMESTAMP_COLUMN = "timestamp";
    public static final String DEVICE_COLUMN = "device";
    public static final String NOTIFICATION_COLUMN = "notification";
    public static final String ID_COLUMN = "id";
    private static final long serialVersionUID = 8704321978956225955L;
    @SerializedName("parameters")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "jsonString", column = @Column(name = "parameters"))
    })
    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_FROM_DEVICE})
    private JsonStringWrapper parameters;
    @SerializedName("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_TO_DEVICE})
    private Long id;
    @SerializedName("timestamp")
    @Column(insertable = false, updatable = false)
    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_TO_DEVICE})
    private Timestamp timestamp;
    @SerializedName("notification")
    @Column
    @NotNull(message = "notification field cannot be null.")
    @Size(min = 1, max = 128, message = "Field cannot be empty. The length of notification should not be more than " +
            "128 symbols.")
    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_FROM_DEVICE})
    private String notification;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", updatable = false)
    @NotNull(message = "device field cannot be null.")
    private Device device;
    @Version
    @Column(name = "entity_version")
    private long entityVersion;

    public DeviceNotification() {
    }

    /**
     * Validates deviceNotification representation. Returns set of strings which are represent constraint violations.
     * Set
     * will be empty if no constraint violations found.
     *
     * @param deviceNotification DeviceCommand that should be validated
     * @param validator          Validator
     * @return Set of strings which are represent constraint violations
     */
    public static Set<String> validate(DeviceNotification deviceNotification, Validator validator) {
        Set<ConstraintViolation<DeviceNotification>> constraintViolations = validator.validate(deviceNotification);
        Set<String> result = new HashSet<>();
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<DeviceNotification> cv : constraintViolations)
                result.add(String.format("Error! property: [%s], value: [%s], message: [%s]",
                        cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
        }
        return result;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getEntityVersion() {
        return entityVersion;
    }

    public void setEntityVersion(long entityVersion) {
        this.entityVersion = entityVersion;
    }

    public Timestamp getTimestamp() {
        return ObjectUtils.cloneIfPossible(timestamp);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = ObjectUtils.cloneIfPossible(timestamp);
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public JsonStringWrapper getParameters() {
        return parameters;
    }

    public void setParameters(JsonStringWrapper parameters) {
        this.parameters = parameters;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public static class Queries {
        public static interface Names {
            static final String DELETE_BY_ID = "DeviceNotification.deleteById";
            static final String DELETE_BY_FK = "DeviceNotification.deleteByFK";
        }

        static interface Values {
            static final String DELETE_BY_ID = "delete from DeviceNotification dn where dn.id = :id";
            static final String DELETE_BY_FK = "delete from DeviceNotification dn where dn.device = :device";
        }

        public static interface Parameters {
            static final String ID = "id";
            static final String DEVICE = "device";
        }
    }
}
