package com.devicehive.client;


import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.SubscriptionFilter;
import com.devicehive.client.model.exceptions.HiveException;

import java.util.Date;
import java.util.List;

/**
 * The API for device commands: {@code /device/{deviceGuid}/command}. Transport declared in the hive context will be used.
 *
 * @see <a href="http://www.devicehive.com/restful#Reference/DeviceCommand">DeviceHive RESTful API: DeviceCommand</a>
 */
public interface CommandsAPI {

    /**
     * Queries commands with the following parameters.
     *
     * @param deviceGuid  device identifier
     * @param start       start timestamp
     * @param end         end timestamp
     * @param commandName filter by command
     * @param status      filter by status
     * @param sortField   either "Date", "Command" or "Status"
     * @param sortOrder   ASC or DESC
     * @param take        number of entities to take
     * @param skip        number of entities that should be skiped (first 'skip' rows will be  skipped)
     * @return a list of {@link DeviceCommand} resources
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceCommand/query">DeviceHive RESTful API: DeviceCommand:
     * query</a>
     */
    List<DeviceCommand> queryCommands(String deviceGuid, Date start, Date end, String commandName,
                                      String status, String sortField, String sortOrder, Integer take, Integer skip,
                                      Integer gridInterval) throws HiveException;

    /**
     * Retrieves command with the following parameters.
     *
     * @param guid a device identifier
     * @param id   a command identifier
     * @return a {@link DeviceCommand} resource associated with required id
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceCommand/get">DeviceHive RESTful API: DeviceCommand:
     * get</a>
     */
    DeviceCommand getCommand(String guid, long id) throws HiveException;

    /**
     * Insert and send a command to a device with specified identifier.
     *
     * @param guid                        a device identifier
     * @param command                     a command to be inserted
     * @param commandUpdateMessageHandler a handler that gets called every time an update on the command status is
     *                                    received. The handler receives the command with updated status as a parameter
     * @return an inserted {@link DeviceCommand} resource
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceCommand/insert">DeviceHive RESTful API: DeviceCommand:
     * insert</a>
     */
    DeviceCommand insertCommand(String guid, DeviceCommand command, HiveMessageHandler<DeviceCommand>
        commandUpdateMessageHandler) throws HiveException;

    /**
     * Updates a command with specified id on a device with specified identifier. Notifies client who sent this command
     * with {@code command/update} message.
     *
     * @param deviceGuid a device identifier
     * @param command    a command resource
     * @throws HiveException if an error occurs during the request execution
     * @see <a href="http://devicehive.com/restful/#Reference/DeviceCommand/update">DeviceHive RESTful API: DeviceCommand:
     * update</a>
     */
    void updateCommand(String deviceGuid, DeviceCommand command) throws HiveException;

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
