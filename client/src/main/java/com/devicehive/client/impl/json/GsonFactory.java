package com.devicehive.client.impl.json;


import com.devicehive.client.impl.json.adapters.AccessTypeAdapter;
import com.devicehive.client.impl.json.adapters.JsonStringWrapperAdapterFactory;
import com.devicehive.client.impl.json.adapters.OptionalAdapterFactory;
import com.devicehive.client.impl.json.adapters.OAuthTypeAdapter;
import com.devicehive.client.impl.json.adapters.UserRoleAdapter;
import com.devicehive.client.impl.json.adapters.UserStatusAdapter;
import com.devicehive.client.impl.json.strategies.AnnotatedStrategy;
import com.devicehive.client.model.AccessType;
import com.devicehive.client.model.OAuthType;
import com.devicehive.client.model.UserRole;
import com.devicehive.client.model.UserStatus;
import com.devicehive.client.model.exceptions.InternalHiveClientException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy;

public class GsonFactory {

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final LoadingCache<Policy, Gson> cache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .build(
                    new CacheLoader<Policy, Gson>() {
                        public Gson load(Policy key) {
                            return createGsonBuilder()
                                    .addDeserializationExclusionStrategy(new AnnotatedStrategy(key))
                                    .addSerializationExclusionStrategy(new AnnotatedStrategy(key))
                                    .create();
                        }
                    });

    private static Gson gson = createGsonBuilder().create();

    public static Gson createGson() {
        return gson;
    }

    public static Gson createGson(Policy policy) throws InternalHiveClientException {
        if (policy == null) {
            return createGson();
        }

        try {
            return cache.get(policy);
        } catch (ExecutionException e) {
            throw new InternalHiveClientException(e.getMessage(), e.getCause());
        }
    }

    private static GsonBuilder createGsonBuilder() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat(TIMESTAMP_FORMAT)
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .registerTypeAdapterFactory(new OptionalAdapterFactory())
                .registerTypeAdapter(AccessType.class, new AccessTypeAdapter())
                .registerTypeAdapter(OAuthType.class, new OAuthTypeAdapter())
                .registerTypeAdapter(UserRole.class, new UserRoleAdapter())
                .registerTypeAdapter(UserStatus.class, new UserStatusAdapter());
    }
}
