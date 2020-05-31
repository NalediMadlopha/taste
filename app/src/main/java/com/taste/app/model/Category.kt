package com.taste.app.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idCategory")
    val idCategory: String,
    @ColumnInfo(name = "strCategory")
    val strCategory: String,
    @ColumnInfo(name = "strCategoryDescription")
    val strCategoryDescription: String,
    @ColumnInfo(name = "strCategoryThumb")
    val strCategoryThumb: String
)