package com.devicehive.client.api;

import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.websocket.model.DeviceNotification;
import com.devicehive.client.websocket.model.HiveMessageHandler;
import com.devicehive.client.model.exceptions.HiveException;

public interface NotificationSubscriptor {

    /**
     * Subscribes a client to notifications. RESTful {@code poll/pollMany} or websocket's {@code subscribe} will be used.
     *
     * @param filter               a subscription filter by device uuids, names etc
     * @param notificationsHandler a delegate that handles received commands
     * @return a subscription id
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceNotification/poll">DeviceHive RESTful API:
     * DeviceNotification: poll</a>
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceNotification/pollMany">DeviceHive RESTful API:
     * DeviceNotification: pollMany</a>
     * @see <a href="http://devicehive.com/restful/#WsReference/Client/notificationsubscribe">DeviceHive WebSocket API: Client:
     * notification/subscribe</a>
     * @see <a href="http://devicehive.com/restful/#WsReference/Client/notificationupdate">DeviceHive WebSocket API: Client:
     * notification/update</a>
     */
    String subscribeForNotifications(SubscriptionFilter filter,
                                     HiveMessageHandler<DeviceNotification> notificationsHandler) throws HiveException;

    /**
     * Unsubscribes a client or device from commands.
     *
     * @param subId a subscription identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#WsReference/Client/notificationunsubscribe">DeviceHive WebSocket API: Client:
     * notification/unsubscribe</a>
     */
    void unsubscribeFromNotification(String subId) throws HiveException;
}
