package com.devicehive.client.websocket.api;


import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.model.HiveMessageHandler;
import com.devicehive.client.model.exceptions.HiveException;

import java.util.Date;
import java.util.List;

/**
 * The API for device notifications: {@code /device/{deviceGuid}/notification}. Transport declared in the
 * hive context will be used.
 *
 * @see <a href="http://www.devicehive.com/restful#Reference/DeviceNotification">DeviceHive RESTful API: DeviceNotification</a>
 */
public interface NotificationsApiWebSocket {

    /**
     * Queries device notifications.
     *
     * @param deviceId         device identifier
     * @param start            start timestamp
     * @param end              end timestamp
     * @param notificationName notification name
     * @param sortOrder        Result list sort order. Available values are ASC and DESC.
     * @param sortField        Result list sort field. Available values are Date (default) and Notification.
     * @param take             Number of records to take from the result list (default is 1000).
     * @param skip             Number of records to skip from the result list.
     * @return If successful, this method returns a list of {@link DeviceNotification} resources in the response body.
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceNotification/query">DeviceHive RESTful API:
     * DeviceNotification: query</a>
     */
    List<DeviceNotification> queryNotifications(String deviceId, Date start, Date end,
                                                String notificationName,
                                                String sortOrder, String sortField, Integer take, Integer skip,
                                                Integer gridInterval) throws HiveException;

    /**
     * Inserts and sends a notification to the client.
     *
     * @param deviceId     a device identifier
     * @param notification a notification to be inserted
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceNotification/insert">DeviceHive RESTful API:
     * DeviceNotification: insert</a>
     */
    DeviceNotification insertNotification(String deviceId, DeviceNotification notification) throws HiveException;

    /**
     * Retrieves information about a notification.
     *
     * @param deviceId       a device identifier
     * @param notificationId a notification identifier
     * @return the requested {@link DeviceNotification} resource
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://www.devicehive.com/restful#Reference/DeviceNotification/get">DeviceHive RESTful API:
     * DeviceNotification: get</a>
     */
    DeviceNotification getNotification(String deviceId, long notificationId) throws HiveException;

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
