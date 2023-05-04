package com.example.cookbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MealAdapter(private val meallist: ArrayList<com.example.cookbook.models.Meal>) : RecyclerView.Adapter<MealAdapter.MyViewHolder>() {

    // This method creates a new ViewHolder object for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        return MyViewHolder(itemView)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return meallist.size
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMeal = meallist[position]
        Glide.with(holder.imageId)
            .load(currentMeal.strMealThumb)
            .into(holder.imageId)
        holder.name.text = currentMeal.strMeal
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageId: ImageView = itemView.findViewById(R.id.imageView)
        val name: TextView = itemView.findViewById(R.id.tvName)
    }
}
