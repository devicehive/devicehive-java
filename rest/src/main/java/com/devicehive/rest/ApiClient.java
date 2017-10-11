package com.devicehive.rest;

import com.devicehive.rest.adapters.DateTimeTypeAdapter;
import com.devicehive.rest.adapters.JsonStringWrapperAdapterFactory;
import com.devicehive.rest.auth.ApiKeyAuth;
import com.devicehive.rest.auth.HttpBasicAuth;
import com.devicehive.rest.utils.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.joda.time.DateTime;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
                .registerTypeAdapterFactory(new JsonStringWrapperAdapterFactory())
                .registerTypeAdapter(DateTime.class,
                        typeAdapter)
                .create();
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//         set your desired log level
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClient = new OkHttpClient().newBuilder()
//                .addInterceptor(logging)
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
        apiAuthorizations.put(authName, authorization);
        okClient = okClient.newBuilder()
                .addInterceptor(authorization)
                .build();
        adapterBuilder.client(okClient);
    }

    public void clearAuthorizations() {
        apiAuthorizations.clear();
        okClient = okClient.newBuilder()
                .build();

        adapterBuilder.client(okClient);
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
        if (type.equals(String.class))
            return new GsonResponseBodyConverterToString<Object>(gson, type);
        else
            return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

}



