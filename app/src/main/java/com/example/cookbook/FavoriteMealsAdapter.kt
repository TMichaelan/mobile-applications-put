package com.example.cookbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.models.Meal

class FavoriteMealsAdapter(private val meals: List<Meal>, private val onMealClickListener: (Meal) -> Unit) : RecyclerView.Adapter<FavoriteMealsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(meals[position], onMealClickListener)
    }

    override fun getItemCount() = meals.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealImage: ImageView = itemView.findViewById(R.id.meal_image)
        private val mealName: TextView = itemView.findViewById(R.id.meal_name)

        fun bind(meal: Meal, onMealClickListener: (Meal) -> Unit) {
            Glide.with(mealImage)
                .load(meal.strMealThumb)
                .into(mealImage)

            mealName.text = meal.strMeal

            itemView.setOnClickListener {
                onMealClickListener(meal)
            }
        }
    }
}
