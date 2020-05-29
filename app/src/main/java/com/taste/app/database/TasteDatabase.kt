package com.taste.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taste.app.model.Category
import com.taste.app.model.Meal

@Database(entities = [Category::class, Meal::class], version = 1, exportSchema = false)
abstract class TasteDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: TasteDatabase? = null

        fun getDatabase(context: Context): TasteDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) { return tempInstance }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasteDatabase::class.java,
                    "taste_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
