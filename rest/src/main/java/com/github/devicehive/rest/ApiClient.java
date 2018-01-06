/*
 *
 *
 *   ApiClient.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.rest;

import com.github.devicehive.rest.adapters.DateTimeTypeAdapter;
import com.github.devicehive.rest.auth.ApiKeyAuth;
import com.github.devicehive.rest.auth.HttpBasicAuth;
import com.github.devicehive.rest.utils.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {

    private Map<String, Interceptor> apiAuthorizations;
    private OkHttpClient okClient;


    public static final String AUTH_API_KEY = "api_key";
    public static final String AUTH_BASIC = "basic";

    private Retrofit.Builder adapterBuilder;

    public ApiClient(String url) {
        apiAuthorizations = new LinkedHashMap<>();
        createDefaultAdapter(url);
    }

    public ApiClient(String url, String[] authNames) {
        this(url);
        for (String authName : authNames) {
            Interceptor auth;
            switch (authName) {
                case AUTH_API_KEY:
                    auth = ApiKeyAuth.newInstance();
                    break;
                case AUTH_BASIC:
                    auth = new HttpBasicAuth();
                    break;
                default:
                    throw new RuntimeException("auth name \"" + authName + "\" not found in available auth names");
            }
            addAuthorization(authName, auth);
        }
    }

    /**
     * Basic constructor for single auth name
     *
     * @param authName
     */
    public ApiClient(String url, String authName) {
        this(url, new String[]{authName});
    }

    /**
     * Helper constructor for single api key
     *
     * @param authName
     * @param apiKey
     */
    public ApiClient(String url, String authName, String apiKey) {
        this(url, authName);
        this.setApiKey(apiKey);
    }

    private void createDefaultAdapter(String url) {
        DateTimeTypeAdapter typeAdapter = new DateTimeTypeAdapter(Const.TIMESTAMP_FORMAT);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class,
                        typeAdapter)
                .create();
        okClient = new OkHttpClient().newBuilder()
                .readTimeout(35, TimeUnit.SECONDS)
                .connectTimeout(35, TimeUnit.SECONDS)
                .build();

        if (!url.endsWith("/")) url = url + "/";

        adapterBuilder = new Retrofit
                .Builder()
                .baseUrl(url)
                .client(okClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonCustomConverterFactory.create(gson));

    }

    public <S> S createService(Class<S> serviceClass) {
        return adapterBuilder.build().create(serviceClass);

    }

    /**
     * Helper method to configure the first api key found
     *
     * @param apiKey
     */
    private void setApiKey(String apiKey) {
        for (Interceptor apiAuthorization : apiAuthorizations.values()) {
            if (apiAuthorization instanceof ApiKeyAuth) {
                ApiKeyAuth keyAuth = (ApiKeyAuth) apiAuthorization;
                keyAuth.setApiKey(apiKey);
                return;
            }
        }
    }

    /**
     * Helper method to configure the username/password for basic auth or password oauth
     *
     * @param username
     * @param password
     */
    private void setCredentials(String username, String password) {
        for (Interceptor apiAuthorization : apiAuthorizations.values()) {
            if (apiAuthorization instanceof HttpBasicAuth) {
                HttpBasicAuth basicAuth = (HttpBasicAuth) apiAuthorization;
                basicAuth.setCredentials(username, password);
                return;
            }
        }
    }

    /**
     * Adds an authorization to be used by the client
     *
     * @param authName
     * @param authorization
     */
    public void addAuthorization(String authName, Interceptor authorization) {
        if (apiAuthorizations.containsKey(authName)) {
            apiAuthorizations.remove(authName);
        }
        apiAuthorizations.put(authName, authorization);
        OkHttpClient.Builder builder = okClient.newBuilder();
        builder.interceptors().clear();
        builder.addInterceptor(authorization);
        okClient = builder.build();
        adapterBuilder.client(okClient);
    }

    public void clearAuthorizations() {
        apiAuthorizations.clear();
        OkHttpClient.Builder builder = okClient.newBuilder();
        builder.interceptors().clear();
        okClient = builder.build();
        adapterBuilder.client(okClient);
    }

    public void recreateClient(String url) {
        createDefaultAdapter(url);
        clearAuthorizations();
    }

    public Map<String, Interceptor> getApiAuthorizations() {
        return apiAuthorizations;
    }

    public void setApiAuthorizations(Map<String, Interceptor> apiAuthorizations) {
        this.apiAuthorizations = apiAuthorizations;
    }

    public Retrofit.Builder getAdapterBuilder() {
        return adapterBuilder;
    }

    public void setAdapterBuilder(Retrofit.Builder adapterBuilder) {
        this.adapterBuilder = adapterBuilder;
    }

    public OkHttpClient getOkClient() {
        return okClient;
    }

    public void setOkClient(OkHttpClient client) {
        okClient = client;
        if (adapterBuilder != null) {
            adapterBuilder.client(client);
        }
    }

    public void addAuthsToOkClient(OkHttpClient okClient) {
        for (Interceptor apiAuthorization : apiAuthorizations.values()) {
            okClient.interceptors().add(apiAuthorization);
        }
    }

    /**
     * Clones the okClient given in parameter, adds the auth interceptors and uses it to configure the Retrofit
     *
     * @param okClient
     */
    public void configureFromOkclient(OkHttpClient okClient) {
        OkHttpClient clone = okClient.newBuilder().build();
        addAuthsToOkClient(clone);
        adapterBuilder.client(clone);
    }
}

/**
 * This wrapper is to take care of this case:
 * when the deserialization fails due to JsonParseException and the
 * expected type is String, then just return the body string.
 */
class GsonResponseBodyConverterToString<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverterToString(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String returned = value.string();
        try {
            return gson.fromJson(returned, type);
        } catch (JsonParseException e) {
            return (T) returned;
        }
    }
}

class GsonCustomConverterFactory extends Converter.Factory {

    public static GsonCustomConverterFactory create(Gson gson) {
        return new GsonCustomConverterFactory(gson);
    }

    private final Gson gson;
    private final GsonConverterFactory gsonConverterFactory;

    private GsonCustomConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        this.gsonConverterFactory = GsonConverterFactory.create(gson);
    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (Objects.equals(type, String.class))
            return new GsonResponseBodyConverterToString<Object>(gson, type);
        else
            return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

}



