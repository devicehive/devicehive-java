package com.devicehive.client.websocket.model;

/**
 * An interface for Device Hive message handlers.
 *
 * @param <M> a type of a message object
 */
public interface HiveMessageHandler<M> {

    /**
     * Gets called whenever a new message arrives.
     *
     * @param message a message object
     */
    void handle(M message);
}
