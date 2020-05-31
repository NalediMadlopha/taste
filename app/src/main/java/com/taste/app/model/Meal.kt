package com.taste.app.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class Meal(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idMeal")
    val idMeal: String,
    @ColumnInfo(name = "strMeal")
    val strMeal: String,
    @ColumnInfo(name = "strCategory")
    val strCategory: String,
    @ColumnInfo(name = "strMealThumb")
    val strMealThumb: String
)