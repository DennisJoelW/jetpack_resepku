package com.example.resepku_jetpack

data class CategoryList(
    val meals: List<CategoryMeal>
)

data class CategoryMeal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)