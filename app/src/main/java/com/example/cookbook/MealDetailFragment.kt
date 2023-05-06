package com.example.cookbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cookbook.models.Meal


class MealDetailFragment : Fragment() {
    private lateinit var meal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            meal = it.getParcelable("meal")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_detail, container, false)

        val mealImage: ImageView = view.findViewById(R.id.meal_image)
        val mealName: TextView = view.findViewById(R.id.meal_name)
        val mealIngredients: TextView = view.findViewById(R.id.meal_ingredients)
        val mealInstructions: TextView = view.findViewById(R.id.meal_instructions)

        val timerFragment = TimerFragment()

        Glide.with(mealImage)
            .load(meal.strMealThumb)
            .into(mealImage)

        mealName.text = meal.strMeal

        val ingredientsAndMeasures = StringBuilder()
        val ingredientFields = listOf(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4, meal.strIngredient5,
            meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9, meal.strIngredient10,
            meal.strIngredient11, meal.strIngredient12, meal.strIngredient13, meal.strIngredient14, meal.strIngredient15,
            meal.strIngredient16, meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
        )
        val measureFields = listOf(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5,
            meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10,
            meal.strMeasure11, meal.strMeasure12, meal.strMeasure13, meal.strMeasure14, meal.strMeasure15,
            meal.strMeasure16, meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
        )

        for (i in ingredientFields.indices) {
            val ingredient = ingredientFields[i]
            val measure = measureFields[i]

            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                ingredientsAndMeasures.append("$ingredient - $measure\n")
            }
        }

        mealIngredients.text = ingredientsAndMeasures.toString()
        mealInstructions.text = meal.strInstructions

        childFragmentManager.beginTransaction()
            .replace(R.id.timer_fragment_container, timerFragment) // Замените контейнер на TimerFragment
            .commit() // Завершите транзакцию

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(meal: Meal) = MealDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("meal", meal)
            }
        }
    }

}