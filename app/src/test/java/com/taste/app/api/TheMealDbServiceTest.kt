package com.taste.app.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.taste.app.util.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class TheMealDbServiceTest  {

    private lateinit var service: TheMealDbService
    private lateinit var mockWebServer: MockWebServer

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(TheMealDbService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCategories should hit the categories endpoint`() {
        enqueueResponse("categories.json")

        service.fetchCategories().execute()
        val request = mockWebServer.takeRequest()

        assertThat(request.path, `is`("/api/json/v1/1/categories.php"))
    }

    @Test
    fun `getCategories should return a null category list if the service returns an error response`() {
        enqueueResponse("error.json")

        val response = service.fetchCategories().execute()
        mockWebServer.takeRequest()

        assertTrue(response.body()?.categories.isNullOrEmpty())
    }

    @Test
    fun `getCategories should return a list of categories if the response is successful`() {
        enqueueResponse("categories.json")

        val response = service.fetchCategories().execute()
        mockWebServer.takeRequest()

        assertTrue(response.isSuccessful)
        assertFalse(response.body()?.categories.isNullOrEmpty())
    }

    @Test
    fun `getMeals should hit the filter endpoint`() {
        enqueueResponse("meals.json")

        service.fetchMeals("Sea Food").execute()
        val request = mockWebServer.takeRequest()

        assertThat(request.path, `is`("/api/json/v1/1/filter.php?c=Sea%20Food"))
    }

    @Test
    fun `getMeals should return a null meal list if the service returns an error response`() {
        enqueueResponse("error.json")

        val response = service.fetchMeals("Sea Food").execute()
        mockWebServer.takeRequest()

        assertTrue(response.body()?.meals.isNullOrEmpty())
    }

    @Test
    fun `getMeals should return a list of meals`() {
        enqueueResponse("meals.json")

        val response = service.fetchMeals("Sea Food").execute()
        mockWebServer.takeRequest()

        assertFalse(response.body()?.meals.isNullOrEmpty())
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
                .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
                mockResponse
                        .setBody(source.readString(Charsets.UTF_8))
        )
    }
}

