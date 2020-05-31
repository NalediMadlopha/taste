package com.taste.app.repo

import androidx.lifecycle.LiveData
import com.taste.app.api.CategoryResponse
import com.taste.app.api.MealResponse
import com.taste.app.model.Category
import com.taste.app.model.Meal
import retrofit2.Response

interface MealRepository {

    fun fetchCategories(): Response<CategoryResponse>

    fun saveCategories(categories: List<Category>)

    fun getCategories(): LiveData<List<Category>>

    fun fetchMeals(category: String): Response<MealResponse>

    fun saveMeals(meals: List<Meal>)

    fun getMeals(category: String): LiveData<List<Meal>>

}
