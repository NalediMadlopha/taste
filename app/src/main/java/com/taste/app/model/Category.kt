package com.taste.app.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    val idCategory: String,
    @ColumnInfo(name = "category")
    val strCategory: String? = "",
    @ColumnInfo(name = "category_description")
    val strCategoryDescription: String? = "",
    @ColumnInfo(name = "category_thumb")
    val strCategoryThumb: String? = ""
)