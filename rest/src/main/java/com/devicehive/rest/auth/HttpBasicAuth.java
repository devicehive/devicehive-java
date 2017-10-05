package com.devicehive.rest.auth;


import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpBasicAuth implements Interceptor {

    private String username;
    private String password;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // If the request already have an authorization (eg. Basic auth), do nothing
        if (request.header("Authorization") == null) {
            String credentials = Credentials.basic(username, password);

            request = request.newBuilder()
                    .addHeader("Authorization", credentials)
                    .addHeader("Accept", "application/json")
                    .build();
            System.out.println(request.headers("Authorization"));
            System.out.println(request.url());

        }
        return chain.proceed(request);
    }
}
