package com.taste.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taste.app.model.Ingredients

@Dao
abstract class IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(ingredients: List<Ingredients>)

    @Query("SELECT * FROM ingredients")
    abstract fun getAll(): LiveData<List<Ingredients>>

    @Query("SELECT * FROM ingredients WHERE idMeal=:id")
    abstract fun findById(id: String): LiveData<Ingredients>

}