package com.github.ramonrabello.favoritehero.network

import com.github.ramonrabello.favoritehero.BuildConfig
import com.github.ramonrabello.favoritehero.core.ktx.toMD5
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for requests made to Marvel API.
 */
class RequestParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val timestamp = "${System.currentTimeMillis() / 1000}"
        val publicKey = BuildConfig.PUBLIC_KEY
        val privateKey = BuildConfig.PRIVATE_KEY
        val hash = "$timestamp$privateKey$publicKey".toMD5()

        val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter(RequestParams.API_KEY, publicKey)
                .addQueryParameter(RequestParams.TIMESTAMP, timestamp)
                .addQueryParameter(RequestParams.HASH, hash)
                .addQueryParameter(RequestParams.LIMIT, 20.toString())
                .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}