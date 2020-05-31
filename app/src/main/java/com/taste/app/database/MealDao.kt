package com.taste.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taste.app.model.Meal

@Dao
abstract class MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(meals: List<Meal>)

    @Query("SELECT * FROM meal")
    abstract fun getAll(): LiveData<List<Meal>>

    @Query("SELECT * FROM meal WHERE strCategory=:category")
    abstract fun getMealsByCategory(category: String): LiveData<List<Meal>>

    @Query("SELECT * FROM meal WHERE idMeal=:id")
    abstract fun findById(id: String): LiveData<Meal>

}