package com.taste.app.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_table")
data class Meal(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    val idMeal: String,
    @ColumnInfo(name = "area")
    val strArea: String? = "",
    @ColumnInfo(name = "category")
    val strCategory: String? = "",
    @ColumnInfo(name = "instructions")
    val strInstructions: String? = "",
    @ColumnInfo(name = "meal")
    val strMeal: String? = "",
    @ColumnInfo(name = "meal_thumb")
    val strMealThumb: String? = "",
    @ColumnInfo(name = "source")
    val strSource: String? = "",
    @ColumnInfo(name = "tags")
    val strTags: String? = "",
    @ColumnInfo(name = "youtube")
    val strYoutube: String? = ""
)