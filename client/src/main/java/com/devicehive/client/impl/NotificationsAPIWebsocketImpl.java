package com.devicehive.client.impl;


import com.devicehive.client.HiveMessageHandler;
import com.devicehive.client.impl.context.WebsocketAgent;
import com.devicehive.client.impl.json.GsonFactory;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.SubscriptionFilter;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.NOTIFICATION_FROM_DEVICE;
import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.NOTIFICATION_TO_DEVICE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Specialization of {@link NotificationsControllerRestImpl} that uses WebSockets transport.
 */
class NotificationsAPIWebsocketImpl extends NotificationsAPIRestImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationsAPIWebsocketImpl.class);

    private final WebsocketAgent websocketAgent;

    /**
     * Initializes the controller with {@link WebsocketAgent} to use for requests.
     *
     * @param websocketAgent a WebsoketAgent to use for requests
     */
    NotificationsAPIWebsocketImpl(WebsocketAgent websocketAgent) {
        super(websocketAgent);
        this.websocketAgent = websocketAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceNotification insertNotification(String guid, DeviceNotification notification) throws HiveException {
        if (notification == null) {
            throw new HiveClientException("Notification cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceNotification: insert requested for device with id {} and notification name {} and params {}",
            guid, notification.getNotification(), notification.getParameters());

        Gson gson = GsonFactory.createGson(NOTIFICATION_FROM_DEVICE);
        String requestId = UUID.randomUUID().toString();

        JsonObject request = new JsonObject();
        request.addProperty("action", "notification/insert");
        request.addProperty("requestId", requestId);
        request.addProperty("deviceGuid", guid);
        request.add("notification", gson.toJsonTree(notification));

        DeviceNotification result = websocketAgent.sendMessage(request, "notification", DeviceNotification.class,
            NOTIFICATION_TO_DEVICE);

        LOGGER.debug("DeviceNotification: insert request proceed for device with id {} and notification name {} and " +
            "params {}. Result id {} and timestamp {}", guid, notification.getNotification(),
            notification.getParameters(), result.getId(), result.getTimestamp());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForNotifications(SubscriptionFilter filter,
                                            HiveMessageHandler<DeviceNotification> notificationsHandler) throws HiveException {
        LOGGER.debug("Client: notification/subscribe requested for filter {},", filter);

        String subId = websocketAgent.subscribeForNotifications(filter, notificationsHandler);

        LOGGER.debug("Client: notification/subscribe proceed for filter {},", filter);

        return subId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromNotification(String subscriptionId) throws HiveException {
        LOGGER.debug("Client: notification/unsubscribe requested.");

        websocketAgent.unsubscribeFromNotifications(subscriptionId);

        LOGGER.debug("Client: notification/unsubscribe proceed.");
    }

}
