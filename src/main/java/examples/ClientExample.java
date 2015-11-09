package examples;

import com.devicehive.client.ApiClient;
import com.devicehive.client.model.JsonStringWrapper;
import com.google.gson.JsonObject;

public class ClientExample {


    static {
        Const.TURN_ON.setCommand(Const.LED_COMMAND);
        Const.TURN_OFF.setCommand(Const.LED_COMMAND);
        JsonObject params = new JsonObject();
        params.addProperty(Const.LED_STATE, true);
        JsonStringWrapper wrapper_param = new JsonStringWrapper();
        wrapper_param.setJsonString(params.toString());

        Const.TURN_ON.setParameters(wrapper_param.toString());
        params.remove(Const.LED_STATE);
        params.addProperty(Const.LED_STATE, false);
        wrapper_param.setJsonString(params.toString());

        Const.TURN_OFF.setParameters(wrapper_param.toString());
    }


    public static void main(String[] args) {
        ApiClient client = new ApiClient(Const.URL, ApiClient.AUTH_BASIC, Const.LOGIN, Const.PASSWORD);
    }
}
