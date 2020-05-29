package com.taste.app.api

import androidx.lifecycle.LiveData
import com.taste.app.model.Category
import com.taste.app.model.Meal
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbService {

    @GET("/categories.php")
    fun getCategories(): LiveData<ApiResponse<List<Category>>>

    @GET("/filter.php")
    fun getMeals(@Query("c") category: String): LiveData<ApiResponse<List<Meal>>>

    @GET("/lookup.php")
    fun getMeal(@Query("i") mealId: String): LiveData<ApiResponse<Meal>>

}