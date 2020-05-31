package com.taste.app.repo

import androidx.lifecycle.LiveData
import com.taste.app.AppExecutors
import com.taste.app.api.CategoryResponse
import com.taste.app.api.MealResponse
import com.taste.app.api.TheMealDbService
import com.taste.app.database.CategoryDao
import com.taste.app.database.MealDao
import com.taste.app.model.Category
import com.taste.app.model.Meal
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepositoryImpl @Inject constructor(
    private val appExecutors: AppExecutors,
    private val categoryDao: CategoryDao,
    private val mealDao: MealDao,
    private val service: TheMealDbService
) : MealRepository {

    override fun fetchCategories(): Response<CategoryResponse> {
        return service.fetchCategories().execute()
    }

    override fun saveCategories(categories: List<Category>) {
        categoryDao.insertAll(categories)
    }

    override fun getCategories(): LiveData<List<Category>> {
        return categoryDao.getAll()
    }

    override fun fetchMeals(category: String): Response<MealResponse> {
        return service.fetchMeals(category).execute()
    }

    override fun saveMeals(meals: List<Meal>) {
        mealDao.insertAll(meals)
    }

    override fun getMeals(category: String): LiveData<List<Meal>> {
        return mealDao.getMealsByCategory(category)
    }

}