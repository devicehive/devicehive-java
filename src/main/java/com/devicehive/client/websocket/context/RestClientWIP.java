package com.devicehive.client.websocket.context;

import com.devicehive.client.ApiClient;
import com.devicehive.client.api.ApiInfoApi;
import com.devicehive.client.json.GsonFactory;
import com.devicehive.client.json.strategies.JsonPolicyApply;
import com.devicehive.client.json.strategies.JsonPolicyDef;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.CommandPollManyResponse;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.HiveMessageHandler;
import com.devicehive.client.model.NotificationPollManyResponse;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.model.exceptions.HiveServerException;
import com.devicehive.client.model.exceptions.InternalHiveClientException;
import com.devicehive.client.websocket.model.HiveEntity;
import com.devicehive.client.websocket.providers.CollectionProvider;
import com.devicehive.client.websocket.providers.HiveEntityProvider;
import com.devicehive.client.websocket.providers.JsonRawProvider;
import com.devicehive.client.websocket.util.Messages;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.internal.Errors;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;

public class RestClientWIP {


    ApiClient apiClient;

    private static final String USER_AUTH_SCHEMA = "Basic";
    private static final String KEY_AUTH_SCHEMA = "Bearer";
    private static final int WAIT_TIMEOUT = 60;
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
     * Creates an instance of a {@link RestClientWIP} for the specified Device Hive RESTful API URI.
     *
     * @param restUri A Device Hive RESTful API endpoint URI
     */
    public RestClientWIP(URI restUri) {
        this.restUri = restUri;
        apiClient = new ApiClient(restUri.toString());
    }

    /**
     * Retrieves a {@link HivePrincipal} obtained during authentication.
     *
     * @return a {@link HivePrincipal} if a client is authenticated; {@code null} otherwise
     */
    public HivePrincipal getHivePrincipal() {
        connectionLock.readLock().lock();
        try {
            return hivePrincipal;
        } finally {
            connectionLock.readLock().unlock();
        }
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
     * Authenticates a client with provided Hive credentials.
     *
     * @param hivePrincipal a Hive principal with client credentials
     * @throws HiveException if an error occurs during authentication
     */
    public void authenticate(final HivePrincipal hivePrincipal) throws HiveException {
        connectionLock.writeLock().lock();
        try {
            if (this.hivePrincipal != null && !this.hivePrincipal.equals(hivePrincipal)) {
                throw new IllegalStateException(Messages.ALREADY_AUTHENTICATED);
            }
            this.hivePrincipal = hivePrincipal;
        } finally {
            connectionLock.writeLock().unlock();
        }
    }

    /**
     * Alias for {@link #disconnect()}.
     */
    public final void close() {
        connectionLock.writeLock().lock();
        try {
            disconnect();
        } finally {
            connectionLock.writeLock().unlock();
        }
    }

    /**
     * Tests if the connection is valid by making a call to the RESTful API endpoint.
     *
     * @return {@code true} if the call returned successfully; {@code false} otherwise
     */
    @SuppressWarnings("unused")
    public boolean checkConnection() {
        try {
            final Response response = buildInvocation("/info", HttpMethod.GET, null, null, null, null).invoke();
            getEntity(response, ApiInfo.class, null);
            return true;
        } catch (HiveException e) {
            return false;
        }
    }


    public <S> S createService(Class<S> serviceClass) {
        return apiClient.createService(serviceClass);
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

    /**
     * Subscribes a client to device(s) commands. Only commands matching the specified filter are handled.
     *
     * @param filter  a commands filter
     * @param handler a handler for received commands
     * @return a subscription identifier
     * @throws HiveException if an error occurs during the request execution
     */
    public String subscribeForCommands(final SubscriptionFilter filter,
                                       final HiveMessageHandler<DeviceCommand> handler) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final String subscriptionIdValue = UUID.randomUUID().toString();
            final SubscriptionDescriptor<DeviceCommand> descriptor = new SubscriptionDescriptor<>(handler, filter);
            commandSubscriptionsStorage.put(subscriptionIdValue, descriptor);

            // If there are multiple device UUIDs passed in the filter, use pollMany implementation; otherwise poll
            // commands for a single device.
            RestSubscription sub = filter.getUuids() != null && filter.getUuids().size() > 1
                    ? new DevicesCommandsRestSubscription(descriptor)
                    : new SingleDeviceCommandsRestSubscription(descriptor);

            final Future<?> commandsSubscription = subscriptionExecutor.submit(sub);
            commandSubscriptionsResults.put(subscriptionIdValue, commandsSubscription);
            return subscriptionIdValue;
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * Removes an existing subscription for commands.
     *
     * @param subscriptionId a subscription id
     * @throws HiveException if an error occurs during the request execution
     */
    public void unsubscribeFromCommands(final String subscriptionId) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final Future<?> commandsSubscription = commandSubscriptionsResults.remove(subscriptionId);
            if (commandsSubscription != null) {
                commandsSubscription.cancel(true);
            }
            commandSubscriptionsStorage.remove(subscriptionId);
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * Subscribes a client to device command updates.
     *
     * @param commandId a command identifier
     * @param guid      a device GUID
     * @param handler   a command handler
     * @throws HiveException if an error occurs during the request execution
     */
    public void subscribeForCommandUpdates(final Long commandId, final String guid,
                                           final HiveMessageHandler<DeviceCommand> handler) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final RestSubscription sub = new DeviceCommandUpdateRestSubscription(commandId, guid, handler);
            subscriptionExecutor.submit(sub);
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * Subscribes a client for device(s) notifications. Only notifications matching the filter are handled.
     *
     * @param filter  a notifications filter
     * @param handler a notifications handler
     * @return a subscription identifier
     * @throws HiveException if an error occurs during the request execution
     */
    public String subscribeForNotifications(final SubscriptionFilter filter,
                                            final HiveMessageHandler<DeviceNotification> handler) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final String subscriptionIdValue = UUID.randomUUID().toString();
            final SubscriptionDescriptor<DeviceNotification> descriptor = new SubscriptionDescriptor<>(handler, filter);
            notificationSubscriptionsStorage.put(subscriptionIdValue, descriptor);

            final RestSubscription sub = new DeviceNotificationsRestSubscription(descriptor);

            final Future<?> notificationsSubscription = subscriptionExecutor.submit(sub);
            notificationSubscriptionResults.put(subscriptionIdValue, notificationsSubscription);
            return subscriptionIdValue;
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * Removes an existing notifications subscription.
     *
     * @param subscriptionId a subscription identifier
     * @throws HiveException if an error occurs during the request execution
     */
    public void unsubscribeFromNotifications(final String subscriptionId) throws HiveException {
        subscriptionsLock.writeLock().lock();
        try {
            final Future<?> notificationsSubscription = notificationSubscriptionResults.remove(subscriptionId);
            if (notificationsSubscription != null) {
                notificationsSubscription.cancel(true);
            }
            notificationSubscriptionsStorage.remove(subscriptionId);
        } finally {
            subscriptionsLock.writeLock().unlock();
        }
    }

    /**
     * Retrieves API info from the server.
     *
     * @return API info
     * @throws HiveException if an error occurs during the request execution
     */
    public ApiInfo getInfo() throws HiveException {
        ApiInfoApi infoApi = apiClient.createService(ApiInfoApi.class);

        try {
            return infoApi.getApiInfo().execute().body();
        } catch (IOException e) {
            throw new HiveException(e.getMessage());
        }
    }

    /**
     * Retrieves the server timestamp.
     *
     * @return the server timestamp
     * @throws HiveException if an error occurs during the request execution
     */
    @SuppressWarnings("unused")
    public DateTime getServerTimestamp() throws HiveException {
        return getInfo().getServerTimestamp();
    }

    /**
     * Retrieves the server RESTful API version.
     *
     * @return the API version
     * @throws HiveException if an error occurs during the request execution
     */
    @SuppressWarnings("unused")
    public String getServerApiVersion() throws HiveException {
        return getInfo().getApiVersion();
    }

    //
    // Protected methods
    //

    /**
     * Executed right before a connection to the server is established.
     *
     * @throws HiveException if something goes wrong
     */
    protected void beforeConnect() throws HiveException {
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
    }

    /**
     * Executed right after a connection to the server is established.
     *
     * @throws HiveException is something goes wrong
     */
    protected void afterConnect() throws HiveException {
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
     * Performs an actual connection shutdown.
     */
    protected void doDisconnect() {
        this.restClient.close();
    }

    /**
     * Executed right after a connection to the server is closed.
     */
    protected void afterDisconnect() {
    }

    //
    // Private methods
    //

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

    //
    // Private classes
    //

    /**
     * A base class for subscription processors.
     */
    private abstract static class RestSubscription implements Runnable {
        private static final Logger LOGGER = LoggerFactory.getLogger(RestSubscription.class);

        /**
         * The actual logic goes here.
         *
         * @throws HiveException if an error occurs during the request execution
         */
        protected abstract void execute() throws HiveException;

        @Override
        public final void run() {
            try {
                execute();
            } catch (Throwable e) {
                LOGGER.error(Messages.SUBSCRIPTION_ERROR, e);
            }
        }
    }

    /**
     * Polls the server for commands sent to specific devices and calls the handler for each command.
     */
    private final class DevicesCommandsRestSubscription extends RestSubscription {
        private final SubscriptionDescriptor<DeviceCommand> descriptor;

        private DevicesCommandsRestSubscription(SubscriptionDescriptor<DeviceCommand> descriptor) {
            this.descriptor = descriptor;
        }

        @Override
        protected void execute() throws HiveException {
            final Map<String, Object> params = new HashMap<>();
            params.put(WAIT_TIMEOUT_PARAM, String.valueOf(WAIT_TIMEOUT));

            final SubscriptionFilter filter = this.descriptor.getFilter();
            if (filter != null) {
                params.put(TIMESTAMP, filter.getTimestamp());
                params.put(NAMES, StringUtils.join(filter.getNames(), SEPARATOR));
                params.put(DEVICE_GUIDS, StringUtils.join(filter.getUuids(), SEPARATOR));
            }

            final Type responseType = new TypeToken<List<CommandPollManyResponse>>() {
            }.getType();

            while (!Thread.currentThread().isInterrupted()) {
                final List<CommandPollManyResponse> responses = RestClientWIP.this.execute("/device/command/poll",
                        HttpMethod.GET, null, params, responseType, JsonPolicyDef.Policy.COMMAND_LISTED);

                for (final CommandPollManyResponse response : responses) {
                    this.descriptor.handleMessage(response.getCommand());
                }

                if (!responses.isEmpty()) {
                    final DateTime newTimestamp = responses.get(responses.size() - 1).getCommand().getTimestamp();
                    params.put(TIMESTAMP, newTimestamp);
                }
            }
        }
    }

    /**
     * Polls the server for commands sent to a specific device and calls the handler for each command.
     */
    private final class SingleDeviceCommandsRestSubscription extends RestSubscription {
        private final SubscriptionDescriptor<DeviceCommand> descriptor;

        private SingleDeviceCommandsRestSubscription(SubscriptionDescriptor<DeviceCommand> descriptor) {
            this.descriptor = descriptor;
        }

        @Override
        protected void execute() throws HiveException {
            final Map<String, Object> params = new HashMap<>();
            params.put(WAIT_TIMEOUT_PARAM, String.valueOf(WAIT_TIMEOUT));

            final SubscriptionFilter filter = this.descriptor.getFilter();
            if (filter != null) {
                params.put(TIMESTAMP, filter.getTimestamp());
                params.put(NAMES, StringUtils.join(filter.getNames(), SEPARATOR));
            }

            final Type responseType = new TypeToken<List<DeviceCommand>>() {
            }.getType();
            final String uri = String.format("/device/%s/command/poll", getHivePrincipal().getPrincipal().getLeft());

            while (!Thread.currentThread().isInterrupted()) {
                final List<DeviceCommand> responses = RestClientWIP.this.execute(uri, HttpMethod.GET, null, params,
                        responseType, JsonPolicyDef.Policy.COMMAND_LISTED);

                for (final DeviceCommand response : responses) {
                    this.descriptor.handleMessage(response);
                }

                if (!responses.isEmpty()) {
                    final DateTime newTimestamp = responses.get(responses.size() - 1).getTimestamp();
                    params.put(TIMESTAMP, newTimestamp);
                }
            }
        }
    }

    /**
     * Polls a server for updates of a specific command for a specific device and calls the handler for each update.
     */
    private final class DeviceCommandUpdateRestSubscription extends RestSubscription {
        private final Long commandId;
        private final String guid;
        private final HiveMessageHandler<DeviceCommand> handler;

        private DeviceCommandUpdateRestSubscription(Long commandId, String guid, HiveMessageHandler<DeviceCommand> handler) {
            this.commandId = commandId;
            this.guid = guid;
            this.handler = handler;
        }

        @Override
        protected void execute() throws HiveException {
            final Map<String, Object> params = new HashMap<>();
            params.put(WAIT_TIMEOUT_PARAM, String.valueOf(WAIT_TIMEOUT));

            final Type responseType = new TypeToken<DeviceCommand>() {
            }.getType();

            DeviceCommand result = null;
            while (result == null && !Thread.currentThread().isInterrupted()) {
                result = RestClientWIP.this.execute(String.format("/device/%s/command/%s/poll", guid, commandId),
                        HttpMethod.GET, null, params, responseType, JsonPolicyDef.Policy.COMMAND_TO_DEVICE);
            }

            handler.handle(result);
        }
    }

    /**
     * Polls the server for notifications sent to a specific device and calls a handler for each notification.
     */
    private final class DeviceNotificationsRestSubscription extends RestSubscription {
        private final SubscriptionDescriptor<DeviceNotification> descriptor;

        private DeviceNotificationsRestSubscription(SubscriptionDescriptor<DeviceNotification> descriptor) {
            this.descriptor = descriptor;
        }

        @Override
        protected void execute() throws HiveException {
            final Map<String, Object> params = new HashMap<>();
            params.put(WAIT_TIMEOUT_PARAM, String.valueOf(WAIT_TIMEOUT));

            final SubscriptionFilter filter = this.descriptor.getFilter();
            if (filter != null) {
                params.put(TIMESTAMP, filter.getTimestamp());
                params.put(NAMES, StringUtils.join(filter.getNames(), SEPARATOR));
                params.put(DEVICE_GUIDS, StringUtils.join(filter.getUuids(), SEPARATOR));
            }

            final Type responseType = new TypeToken<List<NotificationPollManyResponse>>() {
            }.getType();

            while (!Thread.currentThread().isInterrupted()) {
                final List<NotificationPollManyResponse> responses = RestClientWIP.this.execute("/device/notification/poll",
                        HttpMethod.GET, null, params, responseType, JsonPolicyDef.Policy.NOTIFICATION_TO_CLIENT);

                for (final NotificationPollManyResponse response : responses) {
                    this.descriptor.handleMessage(response.getNotification());
                }

                if (!responses.isEmpty()) {
                    final DateTime newTimestamp = responses.get(responses.size() - 1).getNotification().getTimestamp();
                    params.put(TIMESTAMP, newTimestamp);
                }
            }
        }
    }
}
