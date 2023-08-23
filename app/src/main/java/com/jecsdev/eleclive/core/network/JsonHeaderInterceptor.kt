package com.jecsdev.eleclive.core.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class represents the header requests
 **/
class JsonHeaderInterceptor : Interceptor {

    /**
     * @param chain is the interceptor from request headers
     **/
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(request)
    }
}
