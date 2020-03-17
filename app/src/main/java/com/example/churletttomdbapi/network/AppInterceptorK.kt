package com.example.churletttomdbapi.network;

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by Nicolas Churlet on 17/03/2020.
 */
class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = ""
        val originalRequest = chain.request()
        val jsonHeaderRequest =
            originalRequest.newBuilder().addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(jsonHeaderRequest)
    }
}
