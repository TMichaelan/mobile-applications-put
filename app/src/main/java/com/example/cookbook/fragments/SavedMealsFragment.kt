package com.example.cookbook.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.adapters.MealAdapter
import com.example.cookbook.R
import com.example.cookbook.activities.DetailActivity
import com.example.cookbook.db.AppDatabase
import com.example.cookbook.models.Meal
import kotlinx.coroutines.launch

class SavedMealsFragment : Fragment(), MealAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = if (resources.getBoolean(R.bool.is_tablet)) R.layout.meals_grid_and_detail else R.layout.fragment_meals_grid
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycleView)
        val recyclerView: RecyclerView =view.findViewById(R.id.recycleView)
        var columns = 2
        if (resources.getBoolean(R.bool.is_tablet)) columns = 1
        recyclerView.layoutManager = GridLayoutManager(context, columns)
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
        if (resources.getBoolean(R.bool.is_tablet)) {
            val text = view?.findViewById<TextView>(R.id.textView)
            if (text != null) {
                text.visibility = View.GONE
            }

            val mealDetailFragment = MealDetailFragment.newInstance(meal)
            childFragmentManager.beginTransaction()
                .replace(R.id.detail_frame, mealDetailFragment)
                .commit()
        }
        else{
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("meal", meal)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        retainInstance = true
    }
    companion object {
        @JvmStatic
        fun newInstance() = SavedMealsFragment()
    }
}
