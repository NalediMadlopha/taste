package com.taste.app.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import util.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
class IngredientsDaoTest : TasteDatabaseTest() {

    private lateinit var ingredientsDao: IngredientsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        super.setUp()
        ingredientsDao = database.ingredientsDao()
    }

    @Test
    fun insertAll_should_insert_a_list_of_ingredients_to_the_database() {
        database.ingredientsDao().insertAll(dummyIngredients())

        assertNotNull(database.ingredientsDao().getAll().getOrAwaitValue())
    }

    @Test
    fun getAll_should_return_a_list_of_ingredients_from_the_database() {
        database.ingredientsDao().insertAll(dummyIngredients())

        val ingredients = database.ingredientsDao().getAll().getOrAwaitValue()

        assertThat(ingredients.size, equalTo(2))
    }
//
//    @Test
//    fun getMealsByCategory_should_return_a_category_list_of_meals_from_the_database() {
//        val category = "Sea Food"
//        database.mealDao().insertAll(dummyIngredients())
//
//        val meals = database.mealDao().getMealsByCategory(category).getOrAwaitValue()
//
//        assertThat(meals.size, equalTo(1))
//        assertThat(meals.first().strCategory, equalTo(category))
//    }

    @Test
    fun findById_should_return_meal_specific_ingredients_from_the_database() {
        database.ingredientsDao().insertAll(dummyIngredients())

        val ingredients = database.ingredientsDao().findById("2").getOrAwaitValue()

        assertThat(ingredients, equalTo(dummyIngredients()[1]))
    }

}