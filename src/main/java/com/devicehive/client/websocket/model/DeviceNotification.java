package com.devicehive.client.websocket.model;

import com.devicehive.client.json.strategies.JsonPolicyDef;
import com.devicehive.client.model.HiveMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents a device notification, a unit of information dispatched from devices. For more details see <a
 * href="http://www.devicehive.com/restful#Reference/DeviceNotification">Device Notification</a>
 */
public class DeviceNotification implements HiveMessage {

    private static final long serialVersionUID = 8704321978956225955L;
    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_TO_DEVICE})
    private Long id;

    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_TO_DEVICE})
    private DateTime timestamp;

    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_FROM_DEVICE})
    private String notification;

    @JsonPolicyDef({NOTIFICATION_TO_CLIENT, NOTIFICATION_FROM_DEVICE})
    private String parameters;

    public DeviceNotification() {
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public DateTime getTimestamp() {
        return ObjectUtils.cloneIfPossible(timestamp);
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = ObjectUtils.cloneIfPossible(timestamp);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceNotification{");
        sb.append("id=").append(id);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", notification='").append(notification).append('\'');
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
