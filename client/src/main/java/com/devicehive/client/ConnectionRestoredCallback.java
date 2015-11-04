package com.devicehive.client;

/**
 * An interface for websocket's connection restore event handling.
 */
public interface ConnectionRestoredCallback {

    /**
     * Gets called if a websocket's connection is restored.
     */
    void connectionRestored();

}
