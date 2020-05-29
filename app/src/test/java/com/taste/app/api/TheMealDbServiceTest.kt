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
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.getOrAwaitValue

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
        (service.getCategories().getOrAwaitValue() as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()

        assertThat(request.path, `is`("/categories.php"))
    }

    @Test
    fun `getCategories should return a list of categories`() {
        enqueueResponse("categories.json")
        val categories = (service.getCategories().getOrAwaitValue() as ApiSuccessResponse).body

        mockWebServer.takeRequest()

        assertFalse(categories.isNullOrEmpty())
    }

    @Test
    fun `getMeals should hit the filter endpoint`() {
        enqueueResponse("meals.json")
        (service.getMeals("Sea Food").getOrAwaitValue() as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()

        assertThat(request.path, `is`("/filter.php?c=Sea%20Food"))
    }

    @Test
    fun `getMeals should return a list of meals`() {
        enqueueResponse("meals.json")
        val meals = (service.getCategories().getOrAwaitValue() as ApiSuccessResponse).body

        mockWebServer.takeRequest()

        assertFalse(meals.isNullOrEmpty())
    }

    @Test
    fun `getMeal should hit the lookup endpoint`() {
        enqueueResponse("meal.json")
        (service.getMeal("52772").getOrAwaitValue() as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()

        assertThat(request.path, `is`("/lookup.php?i=52772"))
    }

    @Test
    fun `getMeal should return a specific meal`() {
        enqueueResponse("meal.json")
        val meal = (service.getMeal("52772").getOrAwaitValue() as ApiSuccessResponse).body

        mockWebServer.takeRequest()

        assertNotNull(meal)
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

