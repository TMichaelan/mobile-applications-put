package com.example.cookbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.RecipeItemBinding

class RecipeAdapter:RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {
    val recipeList = ArrayList<>()
    class RecipeHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = RecipeItemBinding.bind(item)

        fun bind(recipe) = with(binding){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.bind(recipeList(position))
    }

    override fun getItemCount(): Int {
        return  recipeList.size
    }


}