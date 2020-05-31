package com.taste.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taste.app.model.Category
import com.taste.app.model.Meal

@Database(entities = [Category::class, Meal::class], version = 3, exportSchema = false)
abstract class TasteDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun mealDao(): MealDao

}
