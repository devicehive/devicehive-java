package com.devicehive.client;

/**
 * An interface for callbacks for websocket's connection loss event handling.
 */
public interface ConnectionLostCallback {

    /**
     * Gets called if a websocket's connection is lost.
     */
    void connectionLost();

}
