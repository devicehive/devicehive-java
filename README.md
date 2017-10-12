[![Build Status](https://travis-ci.org/devicehive/devicehive-java.svg?branch=development)](https://travis-ci.org/devicehive/devicehive-java)

DeviceHive Java Client Library & Examples
=========================================

[DeviceHive]: http://devicehive.com "DeviceHive framework"
[DataArt]: http://dataart.com "DataArt"

DeviceHive turns any connected device into the part of Internet of Things.
It provides the communication layer, control software and multi-platform
libraries to bootstrap development of smart energy, home automation, remote
sensing, telemetry, remote control and monitoring software and much more.

Connect embedded Linux using Python or C++ libraries and JSON protocol or
connect AVR, Microchip devices using lightweight C libraries and BINARY protocol.
Develop client applications using HTML5/JavaScript, iOS and Android libraries.
For solutions involving gateways, there is also gateway middleware that allows
to interface with devices connected to it. Leave communications to DeviceHive
and focus on actual product and innovation.

# devicehive-java-client
## Creating client
Creating a client with a new version of library is very simple. First of all you need to initiate `DeviceHive` client:
```java
    DeviceHive deviceHive = DeviceHive.getInstance()
        .init("http://devicehive.server.rest.url", 
        "ws://devicehive.server.websocket.url",
        new TokenAuth("refreshToken", "accessToken"));
```
or you can initiate the client without Websocket url. In this case `DeviceHive` will create the default url based on rest url:
```java
    DeviceHive deviceHive = DeviceHive.getInstance()
        .init("http://devicehive.server.rest.url", 
        new TokenAuth("refreshToken", "accessToken"));
```
## DeviceHive 
* `getInfo()` - gets server info
* `getClusterInfo()` - gets cluster info
* `createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration)` - creates token
* `refreshToken()` - refreshes token
* `getProperty(String name)` - gets property by name
* `setProperty(String name, String value)` - creates property
* `removeProperty(String name)` - removes property by name
* `subscribeCommands(List<String> ids, CommandFilter commandFilter, DeviceCommandsCallback commandsCallback)` - subcribes on specific commands by `device ids` and `command filter` parameters 
* `subscribeNotifications(List<String> ids, NotificationFilter notificationFilter, notificationsCallback)` - subcribes on specific notifications by `device ids` and `notification filter` parameters 
* `unsubscribeCommands(List<String> ids, CommandFilter commandFilter)` -  updates current subcription or unsubscribes at all
* `unsubscribeNotifications(List<String> ids, NotificationFilter notificationFilter)` - updates current subcription or unsubscribes at all
* `listNetworks(NetworkFilter filter)` - gets list of `Network`
* `getNetwork(long id)` - gets `Network` by network's id 
* `removeNetwork(long id)` - removes `Network` by id
* `createNetwork(String name, String description)` -  creates `Network` by id
* `listDevices(DeviceFilter filter)` - gets list of `Device`
* `removeDevice(String id)` - removes `Device` by id
* `getDevice(String id)` - gets existing `Device` by id or creates new `Device`
* `putDevice(String id, String name)` - creates `Device`
## Creating device
To create device you just need an instance of `DeviceHive` and `getDevice(String deviceId)` method:
```java
    Device device = deviceHive.getDevice("example-device-Id");
```
### Working with Device Commands
To create command you just need to call 
`sendCommand(String command, List<Parameter> parameters)` method that will return `DHResponse<DeviceCommand>` with `DeviceCommand` in case of success or `FailureData` with error message and HTTP response code in case of failure:
```java
    DHResponse<DeviceCommand> response = device.sendCommand("command name", parameters);
```
To subscribe on commands you just need to create `CommandFilter` where you can set all needed parameters and call `subscribeCommands(CommandFilter commandFilter, final DeviceCommandsCallback commandCallback)` method:
```java
    CommandFilter commandFilter = new CommandFilter();
        commandFilter.setCommandNames(COM_A, COM_B);
        commandFilter.setStartTimestamp(DateTime.now());
        commandFilter.setMaxNumber(30);
    
    device.subscribeCommands(commandFilter, new DeviceCommandsCallback() {
            public void onSuccess(List<DeviceCommand> commands) {
                }
            }
            public void onFail(FailureData failureData) {
            }
        });
```
### Device Command
`DeviceCommand` contains such properties:
* `id` (read only)
* `user_id` (read only)
* `command` (read only)
* `parameters` (read only)
* `lifetime` (read only)
* `timestamp` (read only)
* `last_updated` (read only)
* `status`
* `result`

`DeviceCommand` contains such methods:
* `updateCommand` - updates current command
* `fetchCommandStatus` - gets command status
* `fetchCommandResult` - gets command result
    

### Working with Device Notifications
There us the same logic regarding `DeviceNotification` to create `notification` you just need to call 
`sendNotification(String notification, List<Parameter> parameters)` method that will return ` DHResponse<DeviceNotification>` with `DeviceNotification` in case of success or `FailureData` with error message and HTTP response code in case of failure:
 ```java
    DHResponse<DeviceNotification> response = device.sendNotification("notification name", parameters);
```
To subscribe on notifications you just need to create `NotificationFilter` where you can set all needed parameters and call `subscribeNotifications(NotificationFilter notificationFilter, DeviceNotificationsCallback notificationCallback)` method:
```java
    NotificationFilter notificationFilter = new NotificationFilter();
        notificationFilter.setNotificationNames(NOTIFICATION_A, NOTIFICATION_B);
        notificationFilter.setStartTimestamp(DateTime.now());
        notificationFilter.setEndTimestamp(DateTime.now().plusSeconds(10));
        
    device.subscribeNotifications(notificationFilter, new DeviceNotificationsCallback(){
            public void onSuccess(List<DeviceNotification> notifications) {
                    }
            public void onFail(FailureData failureData) {
            }
        });
```
DeviceHive Server
------------------
Java Server code was moved to a separate repository: https://github.com/devicehive/devicehive-java


DeviceHive license
------------------

[DeviceHive] is developed by [DataArt] Apps and distributed under Open Source
[MIT license](http://en.wikipedia.org/wiki/MIT_License). This basically means
you can do whatever you want with the software as long as the copyright notice
is included. This also means you don't have to contribute the end product or
modified sources back to Open Source, but if you feel like sharing, you are
highly encouraged to do so!

&copy; Copyright 2017 DataArt Apps &copy; All Rights Reserved
