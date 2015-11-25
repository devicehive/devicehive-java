package examples;

import com.devicehive.client.model.DeviceCommandWrapper;

public class Const {


    //    public static final String URL = "http://192.168.168.183/dh/rest";
    public static final String URL = "http://playground.devicehive.com/api/rest/";
    //Auth
    public static final String MY_API_KEY = "iD8Ktg1wCnAFpbdEmXvkYjN8e0Ku1sTuEnsOaGDDhxI=";
    public static final String LOGIN = "login1";
    public static final String PASSWORD = "password";

    //Device
    public static final String ID = "3d77f31c-bddd-443b-b11c-640946b0581z4123f";
    public static final String NAME = "Graphical Example Device";
    public static final String STATUS = "OFFLINE";
    public static final String DC_NAME = "Graphical Device";
    public static final String DC_VERSION = "1.0";

    //Command
    public static final String LED_COMMAND = "LED";
    public static final String LED_STATE = "state";
    public static final DeviceCommandWrapper TURN_ON = new DeviceCommandWrapper();
    public static final DeviceCommandWrapper TURN_OFF = new DeviceCommandWrapper();


    /**
     * Application name
     */
    public static final String APP_NAME = "Examples set";

    /**
     * If unknown command found or command was used incorrect this message displayed.
     */
    public static final String PARSE_EXCEPTION_MESSAGE = "Unknown command!";

    /**
     * This command identifies that the websocket protocol should be preferred
     */
    public static final String USE_SOCKETS = "useSockets";

    /**
     * Description of useSockets command
     */
    public static final String USE_SOCKETS_DESCRIPTION = "If used then prefer the websocket protocol";

    /**
     * This command line parameter stands for server URL.
     */
//    public static final String URL = "http://playground.devicehive.com/api/rest";

    /**
     * Description of url option.
     */
    public static final String URL_DESCRIPTION = "REST service url";

}
