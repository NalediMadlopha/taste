package com.taste.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taste.app.model.Category

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(categories: List<Category>)

    @Query("SELECT * FROM category")
    abstract fun getAll(): LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE idCategory=:id")
    abstract fun findById(id: String): LiveData<Category>

}