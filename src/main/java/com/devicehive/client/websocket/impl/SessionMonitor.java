package com.devicehive.client.websocket.impl;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.CloseReason;
import javax.websocket.MessageHandler;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A impl session monitor that acts as a watchdog checking that the server responds to the client requests in time.
 * If a response from a server is not received within a certain period of time, the session is forcibly closed and
 * reconnection is initiated.
 */
public class SessionMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionMonitor.class);

    public static final String SESSION_MONITOR_KEY = "SESSION_MONITOR_KEY";
    /**
     * A ping message payload.
     */
    private static final String PING_MESSAGE = "devicehive-client-ping";
    /**
     * Timeout for impl ping/pongs (ms). If no pongs is received during this timeout, the session will be closed.
     */
    private static final long WEBSOCKET_PING_TIMEOUT = TimeUnit.MINUTES.toMillis(2L);
    /**
     * How often pings are sent (seconds).
     */
    private static final long PING_SEND_PERIOD = 30L;
    /**
     * How often the last response time is checked (minutes).
     */
    private static final long PONG_CHECK_PERIOD = 1L;

    private final Date timeOfLastReceivedPong;
    private final Session userSession;
    private ScheduledExecutorService monitor = Executors.newScheduledThreadPool(2);

    public SessionMonitor(Session userSession) {
        this.userSession = userSession;
        this.timeOfLastReceivedPong = new Date();
        addPongHandler();
        startMonitoring();
        sendPings();
    }

    private void addPongHandler() {
        userSession.addMessageHandler(new MessageHandler.Whole<PongMessage>() {
            @Override
            public void onMessage(PongMessage message) {
                LOGGER.debug("Pong received for session {}", userSession.getId());
                timeOfLastReceivedPong.setTime(System.currentTimeMillis());
            }
        });
    }

    private void sendPings() {
        monitor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().setName("pings_sender");
                    userSession.getAsyncRemote().sendPing(ByteBuffer.wrap(PING_MESSAGE.getBytes(Charsets.UTF_8)));
                } catch (IOException ioe) {
                    LOGGER.warn("Unable to send ping", ioe);
                }
            }
        }, 0, PING_SEND_PERIOD, TimeUnit.SECONDS);
    }

    private void startMonitoring() {
        monitor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("monitoring");
                if (System.currentTimeMillis() - timeOfLastReceivedPong.getTime() > WEBSOCKET_PING_TIMEOUT) {
                    LOGGER.info("No pings received from server for a long time. Session will be closed");
                    try {
                        if (userSession.isOpen()) {
                            userSession.close(new CloseReason(CloseReason.CloseCodes.CLOSED_ABNORMALLY,
                                "No pings from server"));
                        }
                    } catch (IOException ioe) {
                        LOGGER.debug("unable to close session");
                    }
                }
            }
        }, 0, PONG_CHECK_PERIOD, TimeUnit.MINUTES);
    }

    public void close() {
        monitor.shutdownNow();
    }

}