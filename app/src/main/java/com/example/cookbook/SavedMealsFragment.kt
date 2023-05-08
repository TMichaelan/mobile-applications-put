package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.db.AppDatabase
import com.example.cookbook.models.Meal
import kotlinx.coroutines.launch

class SavedMealsFragment : Fragment(), MealAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onResume() {
        super.onResume()
        // Загрузка сохраненных блюд из базы данных и отображение их с помощью RecyclerView
        loadSavedMeals()
    }

    private fun loadSavedMeals() {
        val db = AppDatabase.getInstance(requireContext())
        lifecycleScope.launch {
            val savedMeals = db.mealDao().getAllMeals()
            val mealAdapter = MealAdapter(ArrayList(savedMeals), this@SavedMealsFragment)
            recyclerView.adapter = mealAdapter
        }
    }

    override fun onItemClick(meal: Meal) {
        // Здесь вы можете открыть MealDetailActivity и передать информацию о выбранном блюде
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("meal", meal)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavedMealsFragment()
    }
}
