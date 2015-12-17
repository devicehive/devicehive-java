package com.devicehive.client.api;

import com.devicehive.client.websocket.context.SubscriptionFilter;
import com.devicehive.client.websocket.model.DeviceCommand;
import com.devicehive.client.websocket.model.HiveMessageHandler;
import com.devicehive.client.model.exceptions.HiveException;

public interface CommandsSubscriptor {

    /**
     * Subscribes a client or a device to commands. RESTful {@code poll/pollMany} or websocket {@code subscribe}
     * will be used. Once a command is processed, device will be notified by servers's {@code command/update} message.
     *
     * @param filter                a subscription filter by device uuids, names etc
     * @param commandMessageHandler a delegate that handles received commands
     * @return the subscription identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceCommand/poll">DeviceHive RESTful API: DeviceCommand:
     * poll</a>
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceCommand/pollMany">DeviceHive RESTful API: DeviceCommand:
     * pollMany</a>
     * @see <a href="http://devicehive.com/restful/#WsReference/Device/commandsubscribe">DeviceHive WebSocket API: Device:
     * command/subscribe</a>
     * @see <a href="http://devicehive.com/restful/#WsReference/Device/commandupdate">DeviceHive WebSocket API: Device:
     * command/update</a>
     */
    String subscribeForCommands(SubscriptionFilter filter, HiveMessageHandler<DeviceCommand> commandMessageHandler)
            throws HiveException;

    /**
     * Unsubscribes client or device from commands.
     *
     * @param subscriptionId a subscription identifier
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#WsReference/Device/commandunsubscribe">DeviceHive WebSocket API: Device:
     * command/unsubscribe</a>
     */
    void unsubscribeFromCommands(String subscriptionId) throws HiveException;
}
