package com.taste.app.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredients(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idMeal")
    val idMeal: String,
    @ColumnInfo(name = "strArea")
    val strArea: String? = "",
    @ColumnInfo(name = "strCategory")
    val strCategory: String? = "",
    @ColumnInfo(name = "strInstructions")
    val strInstructions: String? = "",
    @ColumnInfo(name = "strMeal")
    val strMeal: String? = "",
    @ColumnInfo(name = "strMealThumb")
    val strMealThumb: String? = "",
    @ColumnInfo(name = "strSource")
    val strSource: String? = "",
    @ColumnInfo(name = "strTags")
    val strTags: String? = "",
    @ColumnInfo(name = "strYoutube")
    val strYoutube: String? = ""
)