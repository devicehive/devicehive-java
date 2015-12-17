package com.devicehive.client.websocket.context;

import com.devicehive.client.json.GsonFactory;
import com.devicehive.client.json.strategies.JsonPolicyDef;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.model.exceptions.HiveServerException;
import com.devicehive.client.model.exceptions.InternalHiveClientException;
import com.devicehive.client.websocket.impl.HiveWebsocketHandler;
import com.devicehive.client.websocket.impl.SessionMonitor;
import com.devicehive.client.websocket.impl.SimpleWebsocketResponse;
import com.devicehive.client.websocket.model.HiveEntity;
import com.devicehive.client.model.HiveMessageHandler;
import com.devicehive.client.websocket.util.Messages;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.ClientEndpointConfig.Configurator;
import javax.websocket.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;
import static com.devicehive.client.websocket.impl.JsonEncoder.*;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

/**
 * A specification of the {@link RestAgent} that uses WebSockets as a transport.
 */
public class WebsocketAgent extends RestAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketAgent.class);
    private static final ClientManager CLIENT_MANAGER = ClientManager.createClient();

    private static final String REQUEST_ID_MEMBER = "requestId";
    private static final String RESPONSE_STATUS_SUCCESS = "success";
    private static final Long WAIT_TIMEOUT = 1L;
    private static final String STATUS = "status";
    private static final String CODE = "code";
    private static final String ERROR = "error";

    /**
     * A storage for mappings of server subscription identifiers to local ones.
     */
    private final ConcurrentMap<String, String> serverToLocalSubIdMap = new ConcurrentHashMap<>();
    /**
     * A storage for command updates. The key is a command identifier, the value is a command handler.
     */
    private final ConcurrentMap<Long, HiveMessageHandler<DeviceCommand>> commandUpdatesHandlerStorage =
            new ConcurrentHashMap<>();
    /**
     * A storage for impl responses. The key is the request identifier, the value is a {@link SettableFuture}
     * which eventually will contain the response from the server.
     */
    private final ConcurrentMap<String, SettableFuture<JsonObject>> websocketResponsesMap = new ConcurrentHashMap<>();
    /**
     * An executor for asynchronous connection state change handling.
     */
    private final ExecutorService connectionStateExecutor = Executors.newSingleThreadExecutor();

    private final Endpoint endpoint;
    private Session currentSession;

    /**
     * Creates an instance of {@link WebsocketAgent} for the specified RESTful API endpoint.
     *
     * @param restUri a RESTful API endpoint URI
     */
    public WebsocketAgent(final URI restUri) {
        super(restUri);
        this.endpoint = new EndpointFactory().createEndpoint();
    }

    /**
     * Sends a message to the server.
     *
     * @param message a {@link HiveEntity HiveEntity} object as JSON.
     * @throws HiveException if an error occurs during the request execution
     */
    public void sendMessage(final JsonObject message) throws HiveException {
        final String requestId = UUID.randomUUID().toString();
        websocketResponsesMap.put(requestId, SettableFuture.<JsonObject>create());
        message.addProperty(REQUEST_ID_MEMBER, requestId);
        rawSend(message);
        processResponse(requestId);
    }

    /**
     * Sends a message to the server.
     *
     * @param message            a {@link HiveEntity HiveEntity} object as JSON.
     * @param responseMemberName a name of a field in the response that contains required object
     * @param responseType       a type of the response object
     * @param policy             a policy that declares a field exclusion strategy for the received object
     * @param <T>                a response type
     * @return an instance of {@code responseType} that represents a server's response
     * @throws HiveException if an error occurs during the request execution
     */
    public <T> T sendMessage(final JsonObject message, final String responseMemberName, final Type responseType,
                             final JsonPolicyDef.Policy policy) throws HiveException {
        final String requestId = UUID.randomUUID().toString();
        websocketResponsesMap.put(requestId, SettableFuture.<JsonObject>create());
        message.addProperty(REQUEST_ID_MEMBER, requestId);
        rawSend(message);
        return processResponse(requestId, responseMemberName, responseType, policy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiInfo getInfo() throws HiveException {
        final String requestId = UUID.randomUUID().toString();

        final JsonObject request = new JsonObject();
        request.addProperty(ACTION_MEMBER, "server/info");
        request.addProperty(REQUEST_ID_MEMBER, requestId);

        ApiInfo apiInfo = sendMessage(request, "info", ApiInfo.class, null);
        final String restUrl = apiInfo.getRestServerUrl();
        apiInfo = super.getInfo();
        apiInfo.setRestServerUrl(restUrl);
        return apiInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForCommands(final SubscriptionFilter filter,
                                       final HiveMessageHandler<DeviceCommand> handler) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final String localId = UUID.randomUUID().toString();
            serverToLocalSubIdMap.put(sendSubscribeForCommands(filter), localId);
            commandSubscriptionsStorage.put(localId, new SubscriptionDescriptor<>(handler, filter));
            return localId;
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromCommands(final String subId) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            commandSubscriptionsStorage.remove(subId);
            final JsonObject request = new JsonObject();
            request.addProperty(ACTION_MEMBER, "command/unsubscribe");
            request.addProperty(SUBSCRIPTION_ID, subId);
            sendMessage(request);
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromNotifications(final String subId) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            notificationSubscriptionsStorage.remove(subId);
            final JsonObject request = new JsonObject();
            request.addProperty(ACTION_MEMBER, "notification/unsubscribe");
            request.addProperty(SUBSCRIPTION_ID, subId);
            sendMessage(request);
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(final HivePrincipal principal) throws HiveException {
        super.authenticate(principal);
        final JsonObject request = new JsonObject();
        request.addProperty(ACTION_MEMBER, "authenticate");
        if (principal.isUser()) {
            request.addProperty("login", principal.getPrincipal().getLeft());
            request.addProperty("password", principal.getPrincipal().getRight());

        } else if (principal.isDevice()) {
            request.addProperty("deviceId", principal.getPrincipal().getLeft());
            request.addProperty("deviceKey", principal.getPrincipal().getRight());

        } else if (principal.isAccessKey()) {
            request.addProperty("accessKey", principal.getPrincipal().getValue());

        } else {
            throw new IllegalArgumentException(Messages.INVALID_HIVE_PRINCIPAL);
        }

        sendMessage(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribeForCommandUpdates(final Long commandId, final String guid,
                                           final HiveMessageHandler<DeviceCommand> handler) {
        subscriptionsLock.writeLock().lock();
        try {
            commandUpdatesHandlerStorage.put(commandId, handler);
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * Asynchronously handles a message from the server.
     *
     * @param jsonMessage a message
     */
    public void handleServerMessage(final JsonObject jsonMessage) {
        subscriptionExecutor.submit(new Runnable() {
            @Override
            public void run() {
                subscriptionsLock.readLock().lock();
                try {
                    switch (jsonMessage.get(ACTION_MEMBER).getAsString()) {
                        case COMMAND_INSERT:
                            final Gson commandInsertGson = GsonFactory.createGson(COMMAND_LISTED);
                            final DeviceCommand commandInsert = commandInsertGson.fromJson(
                                    jsonMessage.getAsJsonObject(COMMAND_MEMBER), DeviceCommand.class);
                            final String localCommandSubId = serverToLocalSubIdMap.get(
                                    jsonMessage.get(SUBSCRIPTION_ID).getAsString());
                            commandSubscriptionsStorage.get(localCommandSubId).handleMessage(commandInsert);
                            break;

                        case COMMAND_UPDATE:
                            final Gson commandUpdateGson = GsonFactory.createGson(COMMAND_UPDATE_TO_CLIENT);
                            final DeviceCommand commandUpdated = commandUpdateGson.fromJson(
                                    jsonMessage.getAsJsonObject(COMMAND_MEMBER), DeviceCommand.class);
                            if (commandUpdatesHandlerStorage.containsKey(commandUpdated.getId())) {
                                commandUpdatesHandlerStorage.remove(commandUpdated.getId()).handle(commandUpdated);
                            }
                            break;

                        case NOTIFICATION_INSERT:
                            final Gson notificationsGson = GsonFactory.createGson(NOTIFICATION_TO_CLIENT);
                            final DeviceNotification notification = notificationsGson.fromJson(
                                    jsonMessage.getAsJsonObject(NOTIFICATION_MEMBER), DeviceNotification.class);
                            final String localNotifSubId = serverToLocalSubIdMap.get(
                                    jsonMessage.get(SUBSCRIPTION_ID).getAsString());
                            notificationSubscriptionsStorage.get(localNotifSubId).handleMessage(notification);
                            break;

                        default:
                            //unknown request
                            LOGGER.error("Server sent unknown message {}", jsonMessage);
                    }
                } catch (InternalHiveClientException e) {
                    LOGGER.error("Cannot retrieve gson from a factory {}", e.getMessage());
                } catch (HiveException e) {
                    e.printStackTrace();
                } finally {
                    subscriptionsLock.readLock().unlock();
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForNotifications(final SubscriptionFilter filter,
                                            final HiveMessageHandler<DeviceNotification> handler) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final String localId = UUID.randomUUID().toString();
            serverToLocalSubIdMap.put(sendSubscribeForNotifications(filter), localId);
            notificationSubscriptionsStorage.put(localId, new SubscriptionDescriptor<>(handler, filter));
            return localId;
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doConnect() throws HiveException {
        super.doConnect();
        final String basicUrl = super.getInfo().getWebSocketServerUrl();
        if (basicUrl == null) {
            throw new HiveException("Can not connect to websockets, endpoint URL is not provided by server");
        }

        final URI wsUri = URI.create(basicUrl + "/client");
        try {
            final String hostname = InetAddress.getLocalHost().getHostName();
            Builder configBuilder = Builder.create();

            configBuilder.configurator(new Configurator() {
                @Override
                public void beforeRequest(Map<String, List<String>> headers) {
                    headers.put("Origin", Collections.singletonList("device://" + hostname));
                }
            });
            CLIENT_MANAGER.connectToServer(endpoint, configBuilder.build(), wsUri);
        } catch (IOException | DeploymentException e) {
            throw new HiveException("Cannot connect to websockets", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDisconnect() {
        try {
            if (currentSession != null) {
                currentSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye."));
            }
        } catch (IOException e) {
            LOGGER.error("Error closing impl session", e);
        }
        super.doDisconnect();
    }

    private void resubscribe() throws HiveException {
        final HivePrincipal principal = getHivePrincipal();
        if (principal != null) {
            authenticate(principal);
        }

        subscriptionsLock.readLock().lock();
        try {
            for (final Map.Entry<String, SubscriptionDescriptor<DeviceCommand>> entry : commandSubscriptionsStorage.entrySet()) {
                serverToLocalSubIdMap.put(sendSubscribeForCommands(entry.getValue().getFilter()), entry.getKey());
            }

            for (final Map.Entry<String, SubscriptionDescriptor<DeviceNotification>> entry : notificationSubscriptionsStorage.entrySet()) {
                serverToLocalSubIdMap.put(sendSubscribeForNotifications(entry.getValue().getFilter()), entry.getKey());
            }
        } finally {
            subscriptionsLock.readLock().unlock();
        }
    }

    private void rawSend(final JsonObject message) {
        connectionLock.readLock().lock();
        try {
            currentSession.getAsyncRemote().sendObject(message);
        } finally {
            connectionLock.readLock().unlock();
        }
    }

    private void processResponse(final String requestId) throws HiveException {
        try {
            final JsonObject result = websocketResponsesMap.get(requestId).get(WAIT_TIMEOUT, TimeUnit.MINUTES);

            if (result != null) {
                final Gson gson = GsonFactory.createGson();
                final SimpleWebsocketResponse response;
                try {
                    response = gson.fromJson(result, SimpleWebsocketResponse.class);
                } catch (JsonSyntaxException e) {
                    throw new HiveServerException(Messages.INCORRECT_RESPONSE_TYPE, 500);
                }

                if (RESPONSE_STATUS_SUCCESS.equals(response.getStatus())) {
                    LOGGER.debug("Request with id: {} proceed successfully", requestId);
                    return;
                }

                final Response.Status.Family errorFamily = Response.Status.Family.familyOf(response.getCode());
                switch (errorFamily) {
                    case SERVER_ERROR:
                        LOGGER.warn("Request id: {}. Error message: {}. Status code: {}", requestId, response.getError(),
                                response.getCode());
                        throw new HiveServerException(response.getError(), response.getCode());

                    case CLIENT_ERROR:
                        LOGGER.warn("Request id: {}. Error message: {}. Status code: {}", requestId, response.getError(),
                                response.getCode());
                        throw new HiveClientException(response.getError(), response.getCode());

                    default:
                        LOGGER.debug("Request id: {}. Error message: {}. Status code: {}", requestId, response.getError(),
                                response.getCode());
                        break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HiveClientException("Interrupted", e);
        } catch (TimeoutException e) {
            throw new HiveServerException(Messages.NO_RESPONSES_FROM_SERVER, SERVICE_UNAVAILABLE.getStatusCode());
        } catch (ExecutionException e) {
            throw new InternalHiveClientException(e.getMessage(), e.getCause());
        } finally {
            websocketResponsesMap.remove(requestId);
        }
    }

    private <T> T processResponse(final String requestId, final String responseMemberName, final Type responseType,
                                  final JsonPolicyDef.Policy receivePolicy) throws HiveException {
        try {
            final JsonObject result = websocketResponsesMap.get(requestId).get(WAIT_TIMEOUT, TimeUnit.MINUTES);

            if (result != null) {

                if (RESPONSE_STATUS_SUCCESS.equals(result.get(STATUS).getAsString())) {
                    LOGGER.debug("Request with id: {} proceed successfully", requestId);

                } else {
                    String error = null;
                    if (result.get(ERROR) instanceof JsonPrimitive) {
                        error = result.get(ERROR).getAsString();
                    }

                    Integer code = null;
                    if (result.get(CODE) instanceof JsonPrimitive) {
                        code = result.get(CODE).getAsInt();
                    }

                    if (code != null) {
                        final Response.Status.Family errorFamily = Response.Status.Family.familyOf(code);
                        switch (errorFamily) {
                            case SERVER_ERROR:
                                LOGGER.warn("Request id: {}. Error message: {}. Status code: {}", requestId, error, code);
                                throw new HiveServerException(error, code);

                            case CLIENT_ERROR:
                                LOGGER.warn("Request id: {}. Error message: {}. Status code: {}", requestId, error, code);
                                throw new HiveClientException(error, code);

                            default:
                                LOGGER.debug("Request id: {}. Error message: {}. Status code: {}", requestId, error, code);
                                break;
                        }
                    } else {
                        LOGGER.warn("Request id: {}. Error message: {}. Status code: {}", requestId, result.get(ERROR),
                                result.get(CODE));
                    }
                }

                final Gson gson = GsonFactory.createGson(receivePolicy);
                final T response;
                try {
                    response = gson.fromJson(result.get(responseMemberName), responseType);
                } catch (JsonSyntaxException e) {
                    throw new InternalHiveClientException(Messages.WRONG_TYPE_RESPONSE);
                }

                return response;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HiveClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            throw new HiveServerException(Messages.NO_RESPONSES_FROM_SERVER, SERVICE_UNAVAILABLE.getStatusCode());
        } catch (ExecutionException e) {
            throw new InternalHiveClientException(e.getMessage(), e.getCause());
        } finally {
            websocketResponsesMap.remove(requestId);
        }
        return null;
    }

    private String sendSubscribeForCommands(final SubscriptionFilter filter) throws HiveException {
        final Gson gson = GsonFactory.createGson();
        final JsonObject request = new JsonObject();
        request.addProperty(ACTION_MEMBER, "command/subscribe");
        request.add("filter", gson.toJsonTree(filter));
        return sendMessage(request, SUBSCRIPTION_ID, String.class, null);
    }

    private String sendSubscribeForNotifications(final SubscriptionFilter filter) throws HiveException {
        final Gson gson = GsonFactory.createGson();
        final JsonObject request = new JsonObject();
        request.addProperty(ACTION_MEMBER, "notification/subscribe");
        request.add("filter", gson.toJsonTree(filter));
        return sendMessage(request, SUBSCRIPTION_ID, String.class, null);
    }

    private final class EndpointFactory {

        private EndpointFactory() {
        }

        private Endpoint createEndpoint() {
            return new Endpoint() {
                @Override
                public void onOpen(final Session session, final EndpointConfig config) {
                    LOGGER.info("[onOpen] User session: {}", session);

                    final SessionMonitor sessionMonitor = new SessionMonitor(session);
                    session.getUserProperties().put(SessionMonitor.SESSION_MONITOR_KEY, sessionMonitor);
                    session.addMessageHandler(new HiveWebsocketHandler(WebsocketAgent.this, websocketResponsesMap));

                    final boolean reconnect = currentSession != null;
                    currentSession = session;

                    if (reconnect) {
                        try {
                            resubscribe();
                        } catch (final HiveException he) {
                            LOGGER.error("Can not restore session context", he);
                            connectionStateExecutor.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION,
                                                "Can not restore session context"));
                                    } catch (IOException e1) {
                                        LOGGER.error("Can not close session", e1);
                                    }
                                }
                            });
                        }
                    }
                }

                @Override
                public void onClose(final Session session, final CloseReason reason) {
                    LOGGER.info("[onClose] Websocket client closed. Reason: {}; Code: {}", reason.getReasonPhrase(),
                            reason.getCloseCode().getCode());

                    final SessionMonitor sessionMonitor = (SessionMonitor) session.getUserProperties()
                            .get(SessionMonitor.SESSION_MONITOR_KEY);

                    if (sessionMonitor != null) {
                        sessionMonitor.close();
                    }
                }

                @Override
                public void onError(Session session, Throwable thr) {
                    LOGGER.error("[onError] ", thr);
                }
            };
        }
    }
}
