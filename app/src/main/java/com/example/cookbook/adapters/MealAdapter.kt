package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.R
import com.example.cookbook.models.Meal

class MealAdapter(
    private val meallist: ArrayList<Meal>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MealAdapter.MyViewHolder>() {

    // This method creates a new ViewHolder object for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        return MyViewHolder(itemView, listener, meallist)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return meallist.size
    }

    // This method binds the data to the ViewHolder object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMeal = meallist[position]
        Glide.with(holder.imageId)
            .load(currentMeal.strMealThumb)
            .into(holder.imageId)
        holder.name.text = currentMeal.strMeal
        holder.itemView.setOnClickListener { listener.onItemClick(currentMeal) }
    }

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(
        itemView: View,
        private val listener: OnItemClickListener,
        private val meallist: ArrayList<Meal>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageId: ImageView = itemView.findViewById(R.id.imageView)
        val name: TextView = itemView.findViewById(R.id.tvName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(meallist[adapterPosition])
        }
    }
}
