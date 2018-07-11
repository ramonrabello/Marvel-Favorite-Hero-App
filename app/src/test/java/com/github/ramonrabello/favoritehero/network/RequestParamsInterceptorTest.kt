package com.github.ramonrabello.favoritehero.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [RequestParamsInterceptor] class.
 */
class RequestParamsInterceptorTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun beforeTest() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun shouldTestIfInterceptorIsWithAllParams() {
        mockWebServer.enqueue(MockResponse())
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(RequestParamsInterceptor()).build()
        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        val request = mockWebServer.takeRequest()
        request.requestUrl.apply {
            assertNotNull(queryParameter(RequestParams.API_KEY))
            assertNotNull(queryParameter(RequestParams.HASH))
            assertNotNull(queryParameter(RequestParams.TIMESTAMP))
        }
    }

    @After
    fun afterTest() {
        mockWebServer.shutdown()
    }
}