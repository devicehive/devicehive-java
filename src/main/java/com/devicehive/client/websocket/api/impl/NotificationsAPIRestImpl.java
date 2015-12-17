package com.devicehive.client.websocket.api.impl;


import com.devicehive.client.websocket.api.NotificationsAPI;
import com.devicehive.client.websocket.context.RestAgent;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.websocket.model.DeviceNotification;
import com.devicehive.client.websocket.model.HiveMessageHandler;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link NotificationsAPI} that uses REST transport.
 */
class NotificationsAPIRestImpl implements NotificationsAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationsAPIRestImpl.class);

    protected static final String DEVICE_NOTIFICATIONS_COLLECTION_PATH = "/device/%s/notification";
    protected static final String DEVICE_NOTIFICATION_RESOURCE_PATH = "/device/%s/notification/%s";

    private final RestAgent restAgent;

    /**
     * Initializes the API with {@link RestAgent} to use for requests.
     *
     * @param restAgent a RestAgent to use for requests
     */
    NotificationsAPIRestImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceNotification> queryNotifications(String guid, Date start, Date end,
                                                       String notificationName, String sortOrder, String sortField,
                                                       Integer take, Integer skip,
                                                       Integer gridInterval) throws HiveException {
        LOGGER.debug("DeviceNotification: query requested with parameters: device id {}, start timestamp {}, " +
            "end timestamp {}, notification name {}, sort order {}, sort field {}, take {}, skip {}, " +
            "grid interval {}", guid, start, end, notificationName, sortOrder, sortField, take, skip, gridInterval);

        String path = String.format(DEVICE_NOTIFICATIONS_COLLECTION_PATH, guid);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start", start);
        queryParams.put("end", end);
        queryParams.put("notification", notificationName);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);
        queryParams.put("gridInterval", gridInterval);

        List<DeviceNotification> result = restAgent.execute(path, HttpMethod.GET, null, queryParams,
            new TypeToken<List<DeviceNotification>>() {}.getType(), NOTIFICATION_TO_CLIENT);

        LOGGER.debug("DeviceNotification: query request proceed with parameters: device id {}, start timestamp {}, " +
            "end timestamp {}, notification name {}, sort order {}, sort field {}, take {}, skip {}, " +
            "grid interval {}", guid, start, end, notificationName, sortOrder, sortField, take, skip, gridInterval);

        return result;
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


        String path = String.format(DEVICE_NOTIFICATIONS_COLLECTION_PATH, guid);
        DeviceNotification result = restAgent.execute(path, HttpMethod.POST, null, null, notification,
            DeviceNotification.class, NOTIFICATION_FROM_DEVICE, NOTIFICATION_TO_DEVICE);

        LOGGER.debug("DeviceNotification: insert request proceed for device with id {} and notification name {} and " +
            "params {}. Result id {} and timestamp {}", guid, notification.getNotification(), notification.getParameters(),
            result.getId(), result.getTimestamp());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceNotification getNotification(String guid, long notificationId) throws HiveException {
        LOGGER.debug("DeviceNotification: get requested for device with id {} and notification id {}", guid,
            notificationId);

        String path = String.format(DEVICE_NOTIFICATION_RESOURCE_PATH, guid, notificationId);
        DeviceNotification result = restAgent.execute(path, HttpMethod.GET, null, DeviceNotification.class,
            NOTIFICATION_TO_CLIENT);

        LOGGER.debug("DeviceNotification: get request proceed for device with id {} and notification id {}",
            guid, notificationId);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForNotifications(SubscriptionFilter filter,
                                            HiveMessageHandler<DeviceNotification> notificationsHandler) throws HiveException {
        LOGGER.debug("Client: notification/subscribe requested for filter {},", filter);

        String subId = restAgent.subscribeForNotifications(filter, notificationsHandler);

        LOGGER.debug("Client: notification/subscribe proceed for filter {},", filter);

        return subId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromNotification(String subId) throws HiveException {
        LOGGER.debug("Client: notification/unsubscribe requested.");

        restAgent.unsubscribeFromNotifications(subId);

        LOGGER.debug("Client: notification/unsubscribe proceed.");
    }

}
