package com.taste.app.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.taste.app.model.Category
import com.taste.app.model.Meal
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class TasteDatabaseTest {

    private lateinit var _database: TasteDatabase

    val database: TasteDatabase
        get() = _database

    @Before
    open fun setUp() {
        _database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasteDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        _database.close()
    }

    companion object {
        fun dummyCategories(): List<Category> {
            return arrayListOf(
                Category(
                    "1",
                    "Breakfast",
                    "Breakfast description",
                    "http://www.themealdb.com/api.php/preview/breakfast"
                ),
                Category(
                    "2",
                    "Sea Food",
                    "Sea Food description",
                    "http://www.themealdb.com/api.php/preview/seafood"
                )
            )
        }

        fun dummyMeals(): List<Meal> {
            return arrayListOf(
                Meal("1",
                    "Apple Frangipan Tart",
                    "Dessert",
                    "http://www.themealdb.com/api.php/preview/1"
                ),
                Meal(
                    "2",
                    "Prawns",
                    "Sea Food",
                    "http://www.themealdb.com/api.php/preview/2"
                )
            )
        }

    }
}