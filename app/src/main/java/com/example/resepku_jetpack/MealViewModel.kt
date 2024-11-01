package com.example.resepku_jetpack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse

class MealViewModel : ViewModel() {
    private val repository = MealRepository()
    val randomMeal = MutableLiveData<MealList?>()
    val popularMeals = MutableLiveData<List<CategoryMeal>?>()
    val _categories = MutableLiveData<List<Category>?>()
    // MutableLiveData for popular meals

    // Fetch a random meal
    fun fetchRandomMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRandomMeal().awaitResponse()
            if (response.isSuccessful) {
                randomMeal.postValue(response.body())
            } else {
                randomMeal.postValue(null)
            }
        }
    }

    fun fetchPopularItems(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPopularItems(category).awaitResponse()
                if (response.isSuccessful) {
                    popularMeals.postValue(response.body()?.meals)
                } else {
                    popularMeals.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("MealViewModel", "Failed to fetch popular items", e)
                popularMeals.postValue(null)
            }
        }
    }

    // Fetch categories
    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCategories().awaitResponse()
                if (response.isSuccessful) {
                    _categories.postValue(response.body()?.categories)
                } else {
                    _categories.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("MealViewModel", "Failed to fetch categories", e)
                _categories.postValue(null)
            }
        }
    }




}
