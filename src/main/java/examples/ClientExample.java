package examples;

import com.google.gson.JsonObject;
import com.devicehive.client.ApiClient;
import com.devicehive.client.model.DeviceCommandWrapper;
import com.devicehive.client.model.JsonStringWrapper;

public class ClientExample {

    private static final String LOGIN = "Dubovik.p.m@gmail.com";
    private static final String PASSWORD = "EDubovik27";
    private static final String LED_COMMAND = "LED";
    private static final String LED_STATE = "state";
    private static final String DEVICE_ID = "3d77f31c-bddd-443b-b11c-640946b0581b";
    private static final DeviceCommandWrapper TURN_ON = new DeviceCommandWrapper();
    private static final DeviceCommandWrapper TURN_OFF = new DeviceCommandWrapper();

    static {
        TURN_ON.setCommand(LED_COMMAND);
        TURN_OFF.setCommand(LED_COMMAND);
        JsonObject params = new JsonObject();
        params.addProperty(LED_STATE, true);
        JsonStringWrapper wrapper_param = new JsonStringWrapper();
        wrapper_param.setJsonString(params.toString());
        TURN_ON.setParameters(wrapper_param.toString());
        params.remove(LED_STATE);
        params.addProperty(LED_STATE, false);
        wrapper_param.setJsonString(params.toString());
        TURN_OFF.setParameters(wrapper_param.toString());
    }


    public static void main(String[] args) {
        ApiClient client = new ApiClient(DeviceExample.URL, ApiClient.AUTH_BASIC, LOGIN, PASSWORD);


    }
}
