package com.example.cookbook

import com.example.cookbook.data.retrofit.RetrofitInstance
import com.example.cookbook.models.Meal
import com.example.cookbook.models.MealList
import retrofit2.Call
import retrofit2.Response

object MealRepository {
    fun getRandomMeal(callback: (ArrayList<Meal>) -> Unit) {
        val mealList = ArrayList<Meal>()
        var count = 0
        val randomMealAmount = 20
        for (i in 1..randomMealAmount) {
            RetrofitInstance.mealAPI.getRandomMeal().enqueue(object : retrofit2.Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.body() != null) {
                        val randomMeal: Meal = response.body()!!.meals[0]
                        mealList += randomMeal
//                        Log.d("MyLog", "meal ${randomMeal.idMeal} name ${randomMeal.strMeal} mealList${mealList}")
                    }
                    count++
                    if (count == randomMealAmount) {
                        callback(mealList)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
//                    Log.d("MyLog", "error: ${t.message.toString()}")
                    count++
                    if (count == randomMealAmount) {
                        callback(mealList)
                    }
                }
            })
        }
    }
}