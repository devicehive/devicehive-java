package com.devicehive.test;

import com.devicehive.client.api.client.Client;
import com.devicehive.client.api.client.CommandsController;
import com.devicehive.client.api.client.HiveClient;
import com.devicehive.client.api.device.SingleHiveDevice;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.JsonStringWrapper;
import com.devicehive.client.model.Transport;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URI;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//TODO remove it
@RunWith(JUnit4.class)
public class PollingTest {

    private final Lock lock = new ReentrantLock();
    private ScheduledExecutorService commandsInsertService = Executors.newSingleThreadScheduledExecutor();
    private volatile boolean greenState = false;
    private volatile boolean redState = false;
    private volatile int i = 0;
    private ScheduledExecutorService commandsUpdatesService = Executors.newSingleThreadScheduledExecutor();
    private HiveClient client;
    @Test
    public void commandsPollingTest() {
        try {
            SingleHiveDevice shd = new SingleHiveDevice(URI.create("http://jk-pc:8080/DeviceHiveJava/rest/"),
                    URI.create("ws://jk-pc:8080/DeviceHiveJava/websocket/"), Transport.REST_ONLY);
            shd.authenticate("E50D6085-2ABA-48E9-B1C3-73C673E414BE".toLowerCase(), "05F94BF509C8");
            client = new Client(URI.create("http://jk-pc:8080/DeviceHiveJava/rest/"),
                    URI.create("ws://jk-pc:8080/DeviceHiveJava/websocket/"), Transport.PREFER_WEBSOCKET);
            client.authenticate("***REMOVED***", "***REMOVED***");
            final CommandsController controller = client.getCommandsController();
            final DeviceCommand command = new DeviceCommand();
            command.setCommand("UpdateLedState");
            commandsInsertService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        try {
                            if (i % 2 == 0) {
                                if (greenState) {
                                    command.setParameters(
                                            new JsonStringWrapper("{\"equipment\":\"LED_G\",\"state\":1}"));
                                    greenState = !greenState;
                                } else {
                                    command.setParameters(
                                            new JsonStringWrapper("{\"equipment\":\"LED_G\",\"state\":0}"));
                                    greenState = !greenState;
                                }
                            } else {
                                if (redState) {
                                    command.setParameters(
                                            new JsonStringWrapper("{\"equipment\":\"LED_R\",\"state\":1}"));
                                    redState = !redState;
                                } else {
                                    command.setParameters(
                                            new JsonStringWrapper("{\"equipment\":\"LED_R\",\"state\":0}"));
                                    redState = !redState;
                                }
                            }
                            i++;
                            System.out.println("Command inserted. Command id: " +
                                    controller.insertCommand("E50D6085-2ABA-48E9-B1C3-73C673E414BE".toLowerCase(),
                                            command).getId());
                        } finally {
                            lock.unlock();
                        }
                    } catch (Exception e) {
                        ;
                    }
                }
            }, 0, 1000 / 2, TimeUnit.MILLISECONDS);
            shd.subscribeForCommands(null);
            commandUpdateServiceStart();
            Thread.currentThread().join(240_000);
        } catch (Exception e) {
            e.printStackTrace();
            TestCase.fail("No exception expected: " + e.getMessage());
        } finally {
            close();
        }
    }

    private void commandUpdateServiceStart() {
        final CommandsController controller = client.getCommandsController();
        commandsUpdatesService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Queue<DeviceCommand> queue = controller.getCommandUpdatesQueue();
                while (!queue.isEmpty()) {
                    DeviceCommand command = queue.poll();
                    System.out.println("command updated: " + command.getId());
                }
            }
        }, 0, 1000 / 2, TimeUnit.MILLISECONDS);
    }

    private void close() {
        try {
            if (client != null)
                client.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            commandsInsertService.shutdown();
            commandsUpdatesService.shutdown();
        }
    }
}
