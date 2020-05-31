package com.taste.app.api

import com.taste.app.model.Meal

data class MealResponse(
    val meals: List<Meal>?
)