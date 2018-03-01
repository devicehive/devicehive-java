DeviceHive Java Client Library & Examples
=========================================

[![Build Status](https://travis-ci.org/devicehive/devicehive-java.svg?branch=master)](https://travis-ci.org/devicehive/devicehive-java)

[DeviceHive](http://devicehive.com)

[DataArt](http://dataart.com)

DeviceHive Client Library is an open source project based on Java. It can be used to build `Android` or `Java` applications.

DeviceHive turns any connected device into the part of Internet of Things.
It provides the communication layer, control software and multi-platform
libraries to bootstrap development of smart energy, home automation, remote
sensing, telemetry, remote control and monitoring software and much more.

# devicehive-java-client
## Creating client
Creating a client with a new version of library is very simple. First of all you need to initiate `DeviceHive` client:
```java
    DeviceHive deviceHive = DeviceHive.getInstance()
        .init("http://devicehive.server.rest.url", "refreshToken", "accessToken");
```
or you can initiate the client without Access token. In this case `DeviceHive` will automaticaly get it:
```java
    DeviceHive deviceHive = DeviceHive.getInstance()
        .init("http://devicehive.server.rest.url", "refreshToken");
```

<details>
 <summary><b>DeviceHive class methods</b></summary>
 
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

</details>

## Creating device
To create device you just need an instance of `DeviceHive` and `getDevice(String deviceId)` that returns `DHResponse<Device>` object where will be `FailureData` object in case of any errors or Device object:
```java
    DHResponse<Device> devicehiveResponse = deviceHive.getDevice("example-device-Id");
    if(devicehiveResponse.isSuccessful(){
	Device device = devicehiveResponse.getData();
    }else{
	FailureData failureData = devicehiveResponse.getFailureData();
	int code = failureData.getCode();
	String message = failureData.getMessage();
    }
```

<details>
 <summary><b>Device class properties and methods</b></summary>
    
`Device` contains such properties and methods:

Properties:
* `id` (read only)
* `name`
* `data`
* `network_id`
* `is_blocked`

Methods:
* `save()` - updates Device
* `getCommands(DateTime startTimestamp, DateTime endTimestamp, int maxNumber) ` - gets Device's DeviceCommands
* `getNotifications(DateTime startTimestamp, DateTime endTimestamp)` -  gets Device's DeviceNotifications
* `sendCommand(String command, List<Parameter> parameters) ` - sends DeviceCommand
* `sendNotification(String notification, List<Parameter> parameters) ` - sends DeviceNotification
* `subscribeCommands(CommandFilter commandFilter, DeviceCommandsCallback commandCallback)` - subscribes for DeviceCommands
* `subscribeNotifications(NotificationFilter notificationFilter, DeviceNotificationsCallback notificationCallback)` - subscribes for DeviceNotifications
* `unsubscribeCommands(CommandFilter commandFilter)` - unsubscribes from DeviceCommands that are not meeting filter criteria
* `unsubscribeAllCommands()` - subscribes from all DeviceCommands
* `unsubscribeNotifications(NotificationFilter notificationFilter)` - subscribes for DeviceNotifications that are not meeting filter criteria
* `unsubscribeAllNotifications()` - subscribes from all DeviceNotifications

 </details>

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

<details>
 <summary><b>DeviceCommand class properties and methods</b></summary>
 
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

</details>

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

<details>
 <summary><b>DeviceNotification class properties</b></summary>
 
`DeviceNotification` contains such properties:
* `device_id` (read only)
* `id` (read only)
* `notification` (read only)
* `parameters` (read only)
* `timestamp` (read only)

 </details>
 
 
 ### Working with Network
 To create `Network` you just need to call  `createNetwork(String name, String description)`  method of  `DeviceHive` class
 
 ```java
 DHResponse<Network> response = deviceHive.createNetwork("My Network's name", "My network's description");
 ```
 also you can get  `Network`  by id  `deviceHive.getNetwork(long id)` or get list of  `Network`  with `deviceHive. listNetworks(NetworkFilter filter)`
 
 ```java
 DHResponse<NetworkVO> response = deviceHive.getNetwork(response.getData().getId());
 ```
 <details>
 <summary><b>Network class properties and methods</b></summary>
 
 Properties:
 * `id` (read only)
 * `name`
 * `description`
 
 Methods:
 * `save()`  - updates `Network`
 
 </details>
 
 ### Working with User
 To create `User` you just need to call  `createUser(String lastLogin, String password, User.RoleEnum role, StatusEnum status, JsonObject data)`  method of  `DeviceHive` class
 
 ```java
 DHResponse<User> response = deviceHive.createUser("javaLibTest", "123456", RoleEnum.ADMIN, StatusEnum.ACTIVE, null);
 ```
 also you can get  `User`  by critria  `deviceHive.getUsers(UserFilter filter)` or just get current  `User`  `deviceHive.getCurrentUser()`
 
 ```java
 DHResponse<User> response = deviceHive.getCurrentUser();
 ```
 <details>
 <summary><b>User class properties and methods</b></summary>
 
 Properties:
 * `id` (read only)
 * `lastLogin`
 * `role`
 * `password` (write only)
 * `data`
 
 Methods:
 * `save()`  - updates `User`
 * `getNetworks()` - gets list of Networks assigned to this user
 * `assignNetwork(long networkId)` - assigns Network to this user
 * `unassignNetwork(long networkId)` - unassigns Network to this user
 
 </details>
 
## Download
Add the JitPack repository to your `project build` file

 ```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Gradle is the only supported build configuration, so just add the dependency to your project `build.gradle` file:
 ```groovy
dependencies {  
   compile 'com.github.devicehive:devicehive-java:3.1.2'
}
```
DeviceHive Server
------------------
Java Server code was moved to a separate repository: https://github.com/devicehive/devicehive-java-server


DeviceHive license
------------------

[DeviceHive] is developed by [DataArt] Apps and distributed under Open Source
[Apache 2.0](https://en.wikipedia.org/wiki/Apache_License). This basically means
you can do whatever you want with the software as long as the copyright notice
is included. This also means you don't have to contribute the end product or
modified sources back to Open Source, but if you feel like sharing, you are
highly encouraged to do so!

&copy; Copyright 2018 DataArt Apps &copy; All Rights Reserved
