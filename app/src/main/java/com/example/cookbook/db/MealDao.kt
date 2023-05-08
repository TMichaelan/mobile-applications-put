package com.example.cookbook.db

import androidx.room.*
import com.example.cookbook.models.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Query("SELECT * FROM meal")
    suspend fun getAllMeals(): List<Meal>

    @Query("SELECT * FROM meal WHERE idMeal = :idMeal")
    fun getMealById(idMeal: String): Meal?

    @Delete
    fun deleteMeal(meal: Meal)
}