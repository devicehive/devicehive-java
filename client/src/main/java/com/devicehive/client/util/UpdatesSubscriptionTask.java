package com.devicehive.client.util;

import com.devicehive.client.context.HiveContext;
import com.devicehive.client.model.DeviceCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.COMMAND_LISTED;

public class UpdatesSubscriptionTask implements Callable<DeviceCommand> {
    private static Logger logger = LoggerFactory.getLogger(UpdatesSubscriptionTask.class);
    private final HiveContext hiveContext;
    private final String path;
    private final Integer waitTimeout;

    public UpdatesSubscriptionTask(HiveContext hiveContext, String path, Integer waitTimeout) {
        this.hiveContext = hiveContext;
        this.path = path;
        this.waitTimeout = waitTimeout;
    }

    @Override
    public DeviceCommand call() throws Exception {
        try {
            DeviceCommand returned = null;
            while (!Thread.currentThread().isInterrupted() && returned == null) {
                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("waitTimeout", waitTimeout);
                returned = hiveContext.getHiveRestClient().executeAsync(path, HttpMethod.GET, null,
                        queryParams, null, DeviceCommand.class, null, COMMAND_LISTED);
                if (returned != null) {
                    hiveContext.getCommandUpdateQueue().add(returned);
                    logger.debug("Command procceed. Id = " + returned.getId() + "Status = " + returned.getStatus());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e.getCause() instanceof InterruptedException)
                Thread.currentThread().interrupt();
        }
        return null;
    }
}
