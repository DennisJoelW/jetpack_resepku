package com.example.resepku_jetpack

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Define data class MealList before use (explained below)
interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") category: String): Call<CategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoriesResponse>
}