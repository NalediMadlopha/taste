package util

import com.taste.app.model.Category
import com.taste.app.model.Meal

fun dummyCategories(): List<Category> {
    return arrayListOf(
        Category(
            "1",
            "Breakfast",
            "Breakfast description",
            "http://www.themealdb.com/api.php/preview/breakfast"
        ),
        Category(
            "2",
            "Sea Food",
            "Sea Food description",
            "http://www.themealdb.com/api.php/preview/seafood"
        )
    )
}

fun dummyMeals(): List<Meal> {
    return arrayListOf(
        Meal("1",
            "Apple Frangipan Tart",
            "Dessert",
            "http://www.themealdb.com/api.php/preview/1"
        ),
        Meal(
            "2",
            "Prawns",
            "Sea Food",
            "http://www.themealdb.com/api.php/preview/2"
        )
    )
}