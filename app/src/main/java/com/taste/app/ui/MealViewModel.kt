package com.taste.app.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taste.app.AppExecutors
import com.taste.app.di.AppModule
import com.taste.app.model.Meal
import com.taste.app.repo.MealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class MealViewModel @Inject constructor(
    private val repository: MealRepository,
    private val appExecutors: AppExecutors,
    @AppModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val success = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Boolean>()

    fun isLoading(): LiveData<Boolean> = loading

    fun isSuccess(): LiveData<Boolean> = success

    fun isError(): LiveData<Boolean> = error

    fun fetchMeals(category: String) {
        loading()
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(ioDispatcher) {
                    repository.fetchMeals(category)
                }
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null && !response.body()?.meals.isNullOrEmpty()) {
                    appExecutors.networkIO().execute {
                        response.body()!!.meals!!.forEach { meal ->
                            meal.strCategory = category
                        }
                        repository.saveMeals(response.body()!!.meals!!)
                    }
                    success()
                } else {
                    error()
                }
            }.onFailure {
                Log.e(this::class.java.simpleName, it.message, it)
                error()
            }
        }
    }

    fun getMeals(category: String): LiveData<List<Meal>> {
        return repository.getMeals(category)
    }

    private fun loading() {
        success.value = false
        error.value = false
        loading.value = true
    }

    private fun success() {
        error.value = false
        loading.value = false
        success.value = true
    }

    private fun error() {
        success.value = false
        loading.value = false
        error.value = true
    }

}
