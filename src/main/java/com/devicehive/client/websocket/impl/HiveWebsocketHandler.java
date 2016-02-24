package com.devicehive.client.websocket.impl;


import com.devicehive.client.websocket.context.WebSocketClient;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.MessageHandler;
import java.util.concurrent.ConcurrentMap;

import static com.devicehive.client.websocket.impl.JsonEncoder.REQUEST_ID_MEMBER;

/**
 * Class that is used to handle messages from server.
 */
public class HiveWebsocketHandler implements MessageHandler.Whole<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HiveWebsocketHandler.class);

    private final WebSocketClient WebSocketClient;
    private final ConcurrentMap<String, SettableFuture<JsonObject>> websocketResponsesMap;

    /**
     * Constructor.
     *
     * @param responsesMap map that contains request id and response association.
     */
    public HiveWebsocketHandler(WebSocketClient WebSocketClient,
                                ConcurrentMap<String, SettableFuture<JsonObject>> responsesMap) {
        this.WebSocketClient = WebSocketClient;
        this.websocketResponsesMap = responsesMap;
    }

    /**
     * Handle messages from server. If message is not a JSON object - HiveServerException will be thrown (server sends
     * smth unparseable and unexpected), else if request identifier is provided then response for some request received,
     * otherwise - command, notification or command update received.
     *
     * @param message message from server.
     */
    @Override
    public void onMessage(String message) {
        JsonObject jsonMessage;
        try {
            jsonMessage = new JsonParser().parse(message).getAsJsonObject();
            if (jsonMessage.has(REQUEST_ID_MEMBER)) {
                SettableFuture<JsonObject> future = websocketResponsesMap.get(
                    jsonMessage.get(REQUEST_ID_MEMBER).getAsString());
                if (future != null) {
                    future.set(jsonMessage);
                }
            } else {
                WebSocketClient.handleServerMessage(jsonMessage);
            }
        } catch (JsonParseException | IllegalStateException ex) {
            LOGGER.error("Server sent incorrect message {}", message);
        }
    }

}
