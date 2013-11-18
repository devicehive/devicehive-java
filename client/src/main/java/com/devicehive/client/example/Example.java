package com.devicehive.client.example;

import com.devicehive.client.api.Client;
import com.devicehive.client.api.CommandsController;
import com.devicehive.client.api.NotificationsController;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.JsonStringWrapper;
import com.devicehive.client.model.Transport;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * MSP430 LaunchPad example
 * <p/>
 * subscribe for notifications,
 * send commands
 * receive notifications
 */
public class Example {

    private static final Logger logger = LoggerFactory.getLogger(Example.class);
    private static final String guid = "c73ccf23-8bf5-4c2c-b330-ead36f469d1a";
    private Client userClient;
    private ScheduledExecutorService notificationsMonitor = Executors.newSingleThreadScheduledExecutor();
    private boolean greenState = false;
    private boolean redState = false;
    private int i = 0;
    private ScheduledExecutorService commandsInsertService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService commandsUpdatesService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String... args) {
        Example example = new Example();
        try {
            example.init();
            example.subscribeForNotifications();
            example.commandInsertServiceStart();
            Thread.currentThread().join(15_000);
        } catch (InterruptedException e) {
            logger.debug(e.getMessage(), e);
        } finally {
            example.close();
        }

    }

    private void init() {
        userClient = new Client(URI.create("http://127.0.0.1:8080/hive/rest/"),
                URI.create("ws://127.0.0.1:8080/hive/websocket/"), Transport.PREFER_WEBSOCKET);
        userClient.authenticate("***REMOVED***", "***REMOVED***");
    }

    private void close() {
        try {
            userClient.close();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            notificationsMonitor.shutdown();
            commandsInsertService.shutdown();
            commandsUpdatesService.shutdown();
        }
    }

    private void commandInsertServiceStart() {
        final CommandsController controller = userClient.getCommandsController();
        commandsInsertService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                DeviceCommand command = new DeviceCommand();
                command.setCommand("UpdateLedState");
                if (i % 2 == 0) {
                    if (greenState) {
                        command.setParameters(new JsonStringWrapper("{\"equipment\":\"LED_G\",\"state\":1}"));
                        greenState = false;
                    } else {
                        command.setParameters(new JsonStringWrapper("{\"equipment\":\"LED_G\",\"state\":0}"));
                        greenState = true;
                    }
                } else {
                    if (redState) {
                        command.setParameters(new JsonStringWrapper("{\"equipment\":\"LED_R\",\"state\":1}"));
                        redState = false;
                    } else {
                        command.setParameters(new JsonStringWrapper("{\"equipment\":\"LED_R\",\"state\":0}"));
                        redState = true;
                    }
                }
                i++;
                controller.insertCommand(guid, command);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void commandUpdateServiceStart() {
        final CommandsController controller = userClient.getCommandsController();
        commandsUpdatesService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Queue<DeviceCommand> queue = controller.getCommandUpdatesQueue();
                while (!queue.isEmpty()) {
                    DeviceCommand command = queue.poll();
                    System.out.println("command updated: " + command.getId());
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void subscribeForNotifications() {
        final NotificationsController controller = userClient.getNotificationsController();
        controller.subscribeForNotifications(null, null, guid);
        notificationsMonitor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Queue<Pair<String, DeviceNotification>> queue = controller.getNotificationsQueue();
                while (!queue.isEmpty()) {
                    Pair<String, DeviceNotification> pair = queue.poll();
                    System.out.println("guid: " + pair.getLeft() + " notification: " + pair.getRight()
                            .getNotification());
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);


    }

}
