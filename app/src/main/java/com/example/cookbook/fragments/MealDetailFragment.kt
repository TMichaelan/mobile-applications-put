package com.example.cookbook.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cookbook.models.Meal
import androidx.room.Room
import com.example.cookbook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.cookbook.db.AppDatabase
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.withContext

class MealDetailFragment : Fragment() {
    private lateinit var meal: Meal
    private lateinit var saveMealButton: MaterialButton
    private lateinit var database: AppDatabase
    private var isImageZoomed = false
    private lateinit var timerFragment: TimerFragment

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        childFragmentManager.putFragment(outState, "timerFragment", timerFragment)
        outState.putBoolean("isImageZoomed", isImageZoomed)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            isImageZoomed = savedInstanceState.getBoolean("isImageZoomed")
            timerFragment = childFragmentManager.getFragment(savedInstanceState, "timerFragment") as TimerFragment
        }

        arguments?.let {
            meal = it.getParcelable("meal")!!

            database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "cookbook-db").build()
        }

        if (savedInstanceState == null) {
            timerFragment = TimerFragment.newInstance()

            childFragmentManager.beginTransaction()
                .replace(R.id.timer_fragment_container, timerFragment)
                .commit()
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

        saveMealButton = view.findViewById(R.id.save_meal_button)
        updateSaveMealButtonIcon()

        saveMealButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val mealInDb = database.mealDao().getMealById(meal.idMeal)
                if (mealInDb != null) {
                    database.mealDao().deleteMeal(meal)
                } else {
                    database.mealDao().insertMeal(meal)
                }
                withContext(Dispatchers.Main) {
                    updateSaveMealButtonIcon()
                }
            }
        }

        val fabShareIngredients = view.findViewById<FloatingActionButton>(R.id.fab_share_ingredients)
        fabShareIngredients.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_ingredients_subject, meal.strMeal))
                putExtra(Intent.EXTRA_TEXT, mealIngredients.text.toString())
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_ingredients_title)))
        }

        mealImage.setOnClickListener {
            if (!isImageZoomed) {
                zoomInImage(mealImage)
            } else {
                zoomOutImage(mealImage)
            }
            isImageZoomed = !isImageZoomed
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealName = meal.strMeal
        val collapsingToolbarLayout: CollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout)
        collapsingToolbarLayout.title = mealName


    }

    private fun zoomInImage(imageView: ImageView) {
        val scaleX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 2.5f)
        val scaleY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 2.5f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 300
        animatorSet.start()
    }

    private fun zoomOutImage(imageView: ImageView) {
        val scaleX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 1f)
        val scaleY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 300
        animatorSet.start()
    }
    private fun updateSaveMealButtonIcon() {
        CoroutineScope(Dispatchers.IO).launch {
            val mealInDb = database.mealDao().getMealById(meal.idMeal)
            val iconRes = if (mealInDb != null) {
                R.drawable.baseline_thumb_up_24
            } else {
                R.drawable.baseline_thumb_up_off_alt_24
            }
            withContext(Dispatchers.Main) {
                saveMealButton.setIconResource(iconRes)
            }
        }
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