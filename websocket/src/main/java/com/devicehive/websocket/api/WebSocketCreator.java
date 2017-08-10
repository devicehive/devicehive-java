package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.*;

interface WebSocketCreator {

    DeviceWS createDeviceWS(DeviceListener listener);

    CommandWS createCommandWS(CommandListener listener);

    ConfigurationWS createConfigurationWS(ConfigurationListener listener);

    NotificationWS createNotificationWS(NotificationListener listener);

    NetworkWS createNetworkWS(NetworkListener listener);

    TokenWS createTokenWS(TokenListener listener);

    UserWS createUserWS(UserListener listener);
}
