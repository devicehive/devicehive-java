package com.devicehive.client.websocket.context;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.json.GsonFactory;
import com.devicehive.client.json.strategies.JsonPolicyApply;
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
import com.devicehive.client.websocket.providers.CollectionProvider;
import com.devicehive.client.websocket.providers.HiveEntityProvider;
import com.devicehive.client.websocket.providers.JsonRawProvider;
import com.devicehive.client.websocket.util.Messages;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.ClientEndpointConfig.Configurator;
import javax.websocket.*;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.devicehive.client.json.strategies.JsonPolicyDef.Policy.*;
import static com.devicehive.client.websocket.impl.JsonEncoder.*;
import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

/**
 * A specification of the {@link RestClientWIP} that uses WebSockets as a transport.
 */
public class WebSocketClient {

    ApiClient apiClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClient.class);
    private static final ClientManager CLIENT_MANAGER = ClientManager.createClient();

    private static final String REQUEST_ID_MEMBER = "requestId";
    private static final String RESPONSE_STATUS_SUCCESS = "success";
    private static final Long WAIT_TIMEOUT = 1L;
    private static final String STATUS = "status";
    private static final String CODE = "code";
    private static final String ERROR = "error";


    private static final String USER_AUTH_SCHEMA = "Basic";
    private static final String KEY_AUTH_SCHEMA = "Bearer";
    private static final String DEVICE_ID_HEADER = "Auth-DeviceID";
    private static final String DEVICE_KEY_HEADER = "Auth-DeviceKey";
    private static final String WAIT_TIMEOUT_PARAM = "waitTimeout";
    private static final String DEVICE_GUIDS = "deviceGuids";
    private static final String NAMES = "names";
    private static final String TIMESTAMP = "timestamp";
    private static final String SEPARATOR = ",";

    /**
     * A lock for connection operations changing the state of the agent.
     */
    protected final ReadWriteLock connectionLock = new ReentrantReadWriteLock(true);
    /**
     * A lock for subscription operations changing the state of the agent.
     */
    protected final ReadWriteLock subscriptionsLock = new ReentrantReadWriteLock(true);
    /**
     * An executor for subscriptions polling.
     */
    protected final ExecutorService subscriptionExecutor = Executors.newFixedThreadPool(50);
    /**
     * A storage for command subscriptions. The key is a subscription identifier, the value is the subscription descriptor.
     */
    protected final ConcurrentMap<String, SubscriptionDescriptor<DeviceCommand>> commandSubscriptionsStorage =
            new ConcurrentHashMap<>();
    /**
     * A storage for notification subscriptions. The key is a subscription identifier, the value is the subscription descriptor.
     */
    protected final ConcurrentMap<String, SubscriptionDescriptor<DeviceNotification>> notificationSubscriptionsStorage =
            new ConcurrentHashMap<>();

    private final URI restUri;
    private final ConcurrentMap<String, Future<?>> commandSubscriptionsResults = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Future<?>> notificationSubscriptionResults = new ConcurrentHashMap<>();

    private Client restClient;
    private HivePrincipal hivePrincipal;
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
     * Creates an instance of {@link WebSocketClient} for the specified RESTful API endpoint.
     *
     * @param restUri a RESTful API endpoint URI
     */
    public WebSocketClient(final URI restUri) {
        this.restUri = restUri;
        apiClient = new ApiClient(restUri.toString());
        this.endpoint = new EndpointFactory().createEndpoint();
    }

    /**
     * Configures and initializes a connection to the URI specified on agent creation.
     *
     * @throws HiveException if a connection error occurs
     */
    public final void connect() throws HiveException {
        connectionLock.writeLock().lock();
        try {
            beforeConnect();
            doConnect();
            afterConnect();
        } finally {
            connectionLock.writeLock().unlock();
        }
    }

    /**
     * Executed right before a connection to the server is established.
     *
     * @throws HiveException if something goes wrong
     */
    protected void beforeConnect() throws HiveException {
    }

    /**
     * Executed right after a connection to the server is established.
     *
     * @throws HiveException is something goes wrong
     */
    protected void afterConnect() throws HiveException {
    }

    /**
     * Shuts down the connection and cleans up resources.
     */
    public final void disconnect() {
        connectionLock.writeLock().lock();
        try {
            beforeDisconnect();
            doDisconnect();
            afterDisconnect();
        } finally {
            connectionLock.writeLock().unlock();
        }
    }

    /**
     * Executed right before a connection to the server is closed.
     */
    protected void beforeDisconnect() {
        MoreExecutors.shutdownAndAwaitTermination(subscriptionExecutor, 1, TimeUnit.MINUTES);
        commandSubscriptionsResults.clear();
        notificationSubscriptionResults.clear();
    }


    /**
     * Executed right after a connection to the server is closed.
     */
    protected void afterDisconnect() {
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
    public ApiInfo getInfo() throws HiveException {
//        final String requestId = UUID.randomUUID().toString();
//
//        final JsonObject request = new JsonObject();
//        request.addProperty(ACTION_MEMBER, "server/info");
//        request.addProperty(REQUEST_ID_MEMBER, requestId);
//
//        ApiInfo apiInfo = sendMessage(request, "info", ApiInfo.class, null);
//
//        final String restUrl = apiInfo.getRestServerUrl();
//        apiInfo = apiClient.getInfo();
//        apiInfo.setRestServerUrl(restUrl);

        ApiInfoApi infoApi = apiClient.createService(ApiInfoApi.class);
        ApiInfo info = infoApi.getApiInfo();
        return info;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Authenticates a client with provided Hive credentials.
     *
     * @param principal a Hive principal with client credentials
     * @throws HiveException if an error occurs during authentication
     */

    public void authenticate(final HivePrincipal principal) throws HiveException {
        connectionLock.writeLock().lock();
        try {
            if (this.hivePrincipal != null && !this.hivePrincipal.equals(hivePrincipal)) {
                throw new IllegalStateException(Messages.ALREADY_AUTHENTICATED);
            }
            this.hivePrincipal = hivePrincipal;
        } finally {
            connectionLock.writeLock().unlock();
        }

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
     * Performs an actual connection to the server.
     *
     * @throws HiveException if an error occurs during connection
     */

    protected void doConnect() throws HiveException {
        this.restClient = JerseyClientBuilder.createClient();
        this.restClient.register(JsonRawProvider.class)
                .register(HiveEntityProvider.class)
                .register(CollectionProvider.class);
        final String basicUrl = getInfo().getWebSocketServerUrl();
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
     * Performs an actual connection shutdown.
     */
    protected void doDisconnect() {
        try {
            if (currentSession != null) {
                currentSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye."));
            }
        } catch (IOException e) {
            LOGGER.error("Error closing impl session", e);
        }
        this.restClient.close();

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

    public HivePrincipal getHivePrincipal() {
        connectionLock.readLock().lock();
        try {
            return hivePrincipal;
        } finally {
            connectionLock.readLock().unlock();
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
                    session.addMessageHandler(new HiveWebsocketHandler(WebSocketClient.this, websocketResponsesMap));

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

    /**
     * Executes a request to the specified RESTful API resource.
     *
     * @param path         a REST resource path
     * @param method       an http method name
     * @param headers      custom headers (authorization headers are added during the request build)
     * @param objectToSend an object to send in the request body (for http methods POST and PUT only)
     * @param sendPolicy   a policy that declares field serialization exclusion strategy for the object being sent
     * @param <S>          the request object type
     * @throws HiveException if an error occurs during the request execution
     */
    public <S> void execute(final String path, final String method, final Map<String, String> headers,
                            final S objectToSend, final JsonPolicyDef.Policy sendPolicy) throws HiveException {
        execute(path, method, headers, null, objectToSend, null, sendPolicy, null);
    }

    /**
     * Executes a request to the specified RESTful API resource.
     *
     * @param path        a REST resource path
     * @param method      an http method name
     * @param headers     custom headers (authorization headers are added during the request build)
     * @param queryParams query params that should be added to the url. Null-valued params are ignored.
     * @throws HiveException if an error occurs during the request execution
     */
    public void execute(final String path, final String method, final Map<String, String> headers,
                        final Map<String, Object> queryParams) throws HiveException {
        execute(path, method, headers, queryParams, null, null, null, null);
    }

    /**
     * Executes a request to the specified RESTful API resource.
     *
     * @param path   a REST resource path
     * @param method an http method name
     * @throws HiveException if an error occurs during the request execution
     */
    public void execute(final String path, final String method) throws HiveException {
        execute(path, method, null, null, null, null, null, null);
    }

    /**
     * Executes a request to the specified RESTful API resource.
     *
     * @param path          a REST resource path
     * @param method        an http method name
     * @param headers       custom headers (authorization headers are added during the request build)
     * @param queryParams   query params that should be added to the url. Null-valued params are ignored.
     * @param responseType  a type of a response object. Should be a class that implements {@link HiveEntity} or a
     *                      collection of such classes
     * @param receivePolicy a policy that declares a field exclusion strategy for a received object
     * @param <R>           a type of a response object
     * @return an instance of a {@code responseType} that represents a server's response
     * @throws HiveException if an error occurs during the request execution
     */
    public <R> R execute(final String path, final String method, final Map<String, String> headers,
                         final Map<String, Object> queryParams,
                         final Type responseType, final JsonPolicyDef.Policy receivePolicy) throws HiveException {
        return execute(path, method, headers, queryParams, null, responseType, null, receivePolicy);
    }

    /**
     * Executes a request to the specified RESTful API resource.
     *
     * @param path          a REST resource path
     * @param method        an http method name
     * @param headers       custom headers (authorization headers are added during the request build)
     * @param responseType  a type of a response object. Should be a class that implements {@link HiveEntity} or a
     *                      collection of such classes
     * @param receivePolicy a policy that declares a field exclusion strategy for a received object
     * @param <R>           a type of a response object
     * @return an instance of {@code responseType} that represents a server's response
     * @throws HiveException if an error occurs during the request execution
     */
    public <R> R execute(final String path, final String method, final Map<String, String> headers,
                         final Type responseType, final JsonPolicyDef.Policy receivePolicy) throws HiveException {
        return execute(path, method, headers, null, null, responseType, null, receivePolicy);
    }

    /**
     * Executes a request to the specified RESTful API resource.
     *
     * @param path          a REST resource path
     * @param method        an http method name
     * @param headers       custom headers (authorization headers are added during the request build)
     * @param queryParams   query params that should be added to the url. Null-valued params are ignored.
     * @param objectToSend  an object to send in the request body (for http methods POST and PUT only)
     * @param responseType  a type of a response object. Should be a class that implements {@link HiveEntity} or a
     *                      collection of such classes
     * @param sendPolicy    a policy that declares a field exclusion strategy for an object being sent
     * @param receivePolicy a policy that declares a field exclusion strategy for a received object
     * @param <S>           the request object type
     * @param <R>           a type of a response object
     * @return an instance of {@code responseType} that represents a server's response
     * @throws HiveException if an error occurs during the request execution
     */
    public <S, R> R execute(final String path, final String method, final Map<String, String> headers,
                            final Map<String, Object> queryParams, final S objectToSend, final Type responseType,
                            final JsonPolicyDef.Policy sendPolicy, final JsonPolicyDef.Policy receivePolicy)
            throws HiveException {
        connectionLock.readLock().lock();
        try {
            final Response response = buildInvocation(path, method, headers, queryParams, objectToSend, sendPolicy).invoke();
            return getEntity(response, responseType, receivePolicy);
        } catch (ProcessingException e) {
            throw new HiveException(Messages.INVOKE_TARGET_ERROR, e.getCause());
        } finally {
            connectionLock.readLock().unlock();
        }
    }

    /**
     * Executes a request to the specified RESTful API resource with {@code Content-Type: x-www-form-urlencoded}.
     *
     * @param path          a REST resource path
     * @param formParams    form parameters
     * @param responseType  a type of a response object. Should be a class that implements {@link HiveEntity} or a
     *                      collection of such classes
     * @param receivePolicy a policy that declares a field exclusion strategy for a received object
     * @param <R>           a type of a response object
     * @return an instance of {@code responseType} that represents server's response
     * @throws HiveException if an error occurs during the request execution
     */
    public <R> R executeForm(final String path, final Map<String, String> formParams, final Type responseType,
                             final JsonPolicyDef.Policy receivePolicy) throws HiveException {
        connectionLock.readLock().lock();
        try {
            final Response response = buildFormInvocation(path, formParams).invoke();
            return getEntity(response, responseType, receivePolicy);
        } catch (ProcessingException e) {
            throw new HiveException(Messages.INVOKE_TARGET_ERROR, e.getCause());
        } finally {
            connectionLock.readLock().unlock();
        }
    }

    private <R> R getEntity(final Response response, final Type responseType,
                            final JsonPolicyDef.Policy receivePolicy) throws HiveException {
        final Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
        switch (statusFamily) {
            case SERVER_ERROR:
                throw new HiveServerException(response.getStatus());

            case CLIENT_ERROR:
                if (response.getStatus() == METHOD_NOT_ALLOWED.getStatusCode()) {
                    throw new InternalHiveClientException(METHOD_NOT_ALLOWED.getReasonPhrase(),
                            response.getStatus());
                }
                final Errors.ErrorMessage errorMessage = response.readEntity(Errors.ErrorMessage.class);
                throw new HiveClientException(errorMessage.getMessage(), response.getStatus());

            case SUCCESSFUL:
                if (responseType == null) {
                    return null;
                }
                if (receivePolicy == null) {
                    return response.readEntity(new GenericType<R>(responseType));
                } else {
                    final Annotation[] readAnnotations = {new JsonPolicyApply.JsonPolicyApplyLiteral(receivePolicy)};
                    return response.readEntity(new GenericType<R>(responseType), readAnnotations);
                }

            default:
                throw new HiveException(Messages.UNKNOWN_RESPONSE);
        }
    }

    private <S> Invocation buildInvocation(final String path, final String method, final Map<String, String> headers,
                                           final Map<String, Object> queryParams, final S objectToSend,
                                           final JsonPolicyDef.Policy sendPolicy) {
        final Invocation.Builder invocationBuilder = createTarget(path, queryParams)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);

        for (final Map.Entry<String, String> entry : getAuthHeaders().entrySet()) {
            invocationBuilder.header(entry.getKey(), entry.getValue());
        }

        if (headers != null) {
            for (final Map.Entry<String, String> customHeader : headers.entrySet()) {
                invocationBuilder.header(customHeader.getKey(), customHeader.getValue());
            }
        }

        if (objectToSend != null) {
            final Entity<S> entity;
            if (sendPolicy != null) {
                entity = Entity.entity(objectToSend, MediaType.APPLICATION_JSON_TYPE,
                        new Annotation[]{new JsonPolicyApply.JsonPolicyApplyLiteral(sendPolicy)});
            } else {
                entity = Entity.entity(objectToSend, MediaType.APPLICATION_JSON_TYPE);
            }
            return invocationBuilder.build(method, entity);
        } else {
            return invocationBuilder.build(method);
        }
    }

    private Invocation buildFormInvocation(final String path, final Map<String, String> formParams) throws HiveException {
        final Invocation.Builder invocationBuilder = createTarget(path, null)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_TYPE);

        for (final Map.Entry<String, String> entry : getAuthHeaders().entrySet()) {
            invocationBuilder.header(entry.getKey(), entry.getValue());
        }

        final Entity<Form> entity;
        if (formParams != null) {
            final Form f = new Form();
            for (final Map.Entry<String, String> entry : formParams.entrySet()) {
                f.param(entry.getKey(), entry.getValue());
            }
            entity = Entity.entity(f, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
            return invocationBuilder.build(HttpMethod.POST, entity);
        }
        throw new InternalHiveClientException(Messages.FORM_PARAMS_ARE_NULL);
    }

    private Map<String, String> getAuthHeaders() {
        final Map<String, String> headers = new HashMap<>();

        final HivePrincipal hivePrincipal = getHivePrincipal();
        if (hivePrincipal != null) {
            final Pair<String, String> principal = hivePrincipal.getPrincipal();
            if (hivePrincipal.isUser()) {
                final String decodedAuth = principal.getLeft() + ":" + principal.getRight();

                final String encodedAuth = Base64.encodeBase64String(decodedAuth.getBytes(Charsets.UTF_8));
                headers.put(HttpHeaders.AUTHORIZATION, USER_AUTH_SCHEMA + " " + encodedAuth);

            } else if (hivePrincipal.isDevice()) {
                headers.put(DEVICE_ID_HEADER, principal.getLeft());
                headers.put(DEVICE_KEY_HEADER, principal.getRight());

            } else if (hivePrincipal.isAccessKey()) {
                headers.put(HttpHeaders.AUTHORIZATION, KEY_AUTH_SCHEMA + " " + principal.getValue());
            }
        }

        return headers;
    }

    private WebTarget createTarget(final String path, final Map<String, Object> queryParams) {
        WebTarget target = restClient.target(restUri).path(path);
        if (queryParams != null) {
            for (final Map.Entry<String, Object> entry : queryParams.entrySet()) {
                final Object value = entry.getValue() instanceof Date
                        ? new SimpleDateFormat(GsonFactory.TIMESTAMP_FORMAT)
                        : entry.getValue();
                target = target.queryParam(entry.getKey(), value);
            }
        }
        return target;
    }
}
