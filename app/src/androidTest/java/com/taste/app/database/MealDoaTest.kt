package com.taste.app.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import util.dummyMeals
import util.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
class MealDaoTest : TasteDatabaseTest() {

    private lateinit var mealDao: MealDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        super.setUp()
        mealDao = database.mealDao()
    }

    @Test
    fun insertAll_should_insert_a_list_of_meals_to_the_database() {
        database.mealDao().insertAll(dummyMeals())

        Assert.assertNotNull(database.mealDao().getAll().getOrAwaitValue())
    }

    @Test
    fun getAll_should_return_a_list_of_meals_from_the_database() {
        database.mealDao().insertAll(dummyMeals())

        val meals = database.mealDao().getAll().getOrAwaitValue()

        Assert.assertThat(meals.size, CoreMatchers.equalTo(2))
    }

    @Test
    fun getMealsByCategory_should_return_a_category_list_of_meals_from_the_database() {
        val category = "Sea Food"
        database.mealDao().insertAll(dummyMeals())

        val meals = database.mealDao().getMealsByCategory(category).getOrAwaitValue()

        Assert.assertThat(meals.size, CoreMatchers.equalTo(1))
        Assert.assertThat(meals.first().strCategory, CoreMatchers.equalTo(category))
    }

    @Test
    fun findById_should_return_a_specific_meal_from_the_database() {
        database.mealDao().insertAll(dummyMeals())

        val meal = database.mealDao().findById("2").getOrAwaitValue()

        Assert.assertThat(meal, CoreMatchers.equalTo(dummyMeals()[1]))
    }

}