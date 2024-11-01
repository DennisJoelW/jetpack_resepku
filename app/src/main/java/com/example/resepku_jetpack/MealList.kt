package com.example.resepku_jetpack

data class MealList(
    val meals: List<Meal>
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String
)