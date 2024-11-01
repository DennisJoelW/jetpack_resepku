package com.example.resepku_jetpack

import com.example.resepku_jetpack.MealList
import com.example.resepku_jetpack.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class MealRepository {
    fun getRandomMeal(): Call<MealList> {
        return RetrofitInstance.api.getRandomMeal()
    }

    fun getPopularItems(category: String): Call<CategoryList> {
        return RetrofitInstance.api.getPopularItems(category)
    }

    fun getCategories(): Call<CategoriesResponse> {
        return RetrofitInstance.api.getCategories()
    }

}