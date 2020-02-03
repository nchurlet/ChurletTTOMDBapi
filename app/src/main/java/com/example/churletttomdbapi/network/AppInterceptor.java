package com.example.churletttomdbapi.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicolas Churlet on 11/04/2018.
 */

public class AppInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = "";
        Request originalRequest = chain.request();
        Request jsonHeaderRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer " + token).build();
        Response response = chain.proceed(jsonHeaderRequest);
        return response;
    }
}