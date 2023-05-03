package com.example.cookbook.retrofit

import com.example.cookbook.models.CategoryList
import com.example.cookbook.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String):Call<MealList>

    @GET("search.php?")
    fun getMealByName(@Query("s") s:String):Call<MealList>



}