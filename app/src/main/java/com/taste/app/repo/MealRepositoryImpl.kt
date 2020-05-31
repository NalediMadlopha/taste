package com.taste.app.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.taste.app.AppExecutors
import com.taste.app.api.CategoryResponse
import com.taste.app.api.MealResponse
import com.taste.app.api.TheMealDbService
import com.taste.app.database.CategoryDao
import com.taste.app.database.MealDao
import com.taste.app.model.Category
import com.taste.app.model.Meal
import retrofit2.Call
import retrofit2.Callback
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

    override fun fetchCategories() {
        service.fetchCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                val data = response.body()?.categories as ArrayList<Category>
                appExecutors.networkIO().execute { categoryDao.insertAll(data) }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.i(this::class.simpleName, call.toString(), t)
            }
        })
    }

    override fun getCategories(): LiveData<List<Category>> {
        return categoryDao.getAll()
    }

    override fun fetchMeals(category: String) {
        service.fetchMeals(category).enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {
                val data = response.body()?.meals as ArrayList<Meal>
                appExecutors.networkIO().execute { mealDao.insertAll(data) }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.i(this::class.simpleName, call.toString(), t)
            }
        })
    }

    override fun getMeals(category: String): LiveData<List<Meal>> {
        return mealDao.getMealsByCategory(category)
    }

}