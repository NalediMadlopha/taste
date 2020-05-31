package com.taste.app.repo

import androidx.lifecycle.LiveData
import com.taste.app.model.Category
import com.taste.app.model.Meal

interface MealRepository {

    fun fetchCategories()

    fun getCategories(): LiveData<List<Category>>

    fun fetchMeals(category: String)

    fun getMeals(category: String): LiveData<List<Meal>>

}
