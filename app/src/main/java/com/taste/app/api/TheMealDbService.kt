package com.taste.app.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbService {

    @GET("/api/json/v1/1/categories.php")
    fun fetchCategories(): Call<CategoryResponse>

    @GET("/api/json/v1/1/filter.php")
    fun fetchMeals(@Query("c") category: String): Call<MealResponse>

}