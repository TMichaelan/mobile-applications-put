package com.example.cookbook.retrofit

import com.example.cookbook.models.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

}