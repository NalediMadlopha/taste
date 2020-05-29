package com.taste.app.api

import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("message")

        val (errorMessage) = ApiResponse.create<String>(exception)

        assertThat(errorMessage, `is`("message"))
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
            400,
            "error".toResponseBody(null)
        )

        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>

        assertThat(errorMessage, `is`("error"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
            .create<String>(Response.success("body")) as ApiSuccessResponse<String>

        assertThat(apiResponse.body, `is`("body"))
    }
}