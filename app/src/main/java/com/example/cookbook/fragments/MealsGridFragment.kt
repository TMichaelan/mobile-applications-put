package com.example.cookbook.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.adapters.MealAdapter
import com.example.cookbook.R
import com.example.cookbook.activities.DetailActivity
import com.example.cookbook.models.Meal

class MealsGridFragment : Fragment(), MealAdapter.OnItemClickListener {
    private var selectedMeal: Meal? = null
    private var textVisible: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutId = if (resources.getBoolean(R.bool.is_tablet)) R.layout.meals_grid_and_detail else R.layout.fragment_meals_grid
        return inflater.inflate(layoutId, container)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            textVisible = it.getBoolean("textVisible", true)
        }

        // Получение списка блюд из аргументов
        val mealList = arguments?.getSerializable("mealList") as? ArrayList<Meal>

        if (mealList != null) {
            // Assign mealList to ItemAdapter
            val itemAdapter = MealAdapter(mealList, this)
            // Set the LayoutManager that
            // this RecyclerView will use.
            val recyclerView: RecyclerView = view.findViewById(R.id.recycleView)
            var columns = 2
            if (resources.getBoolean(R.bool.is_tablet)) columns = 1
            recyclerView.layoutManager = GridLayoutManager(context, columns)
            // adapter instance is set to the
            // recyclerview to inflate the items.
            recyclerView.adapter = itemAdapter
        }

        if (resources.getBoolean(R.bool.is_tablet)) {
            val text = view.findViewById<TextView>(R.id.textView)
            text.visibility = if (textVisible) View.VISIBLE else View.GONE
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("textVisible", textVisible)
    }



    override fun onItemClick(meal: Meal) {
        selectedMeal = meal

        if (resources.getBoolean(R.bool.is_tablet)) {
            val text = view?.findViewById<TextView>(R.id.textView)
            if (text != null) {
                text.visibility = View.GONE
                textVisible = false
            }
            val mealDetailFragment = MealDetailFragment.newInstance(meal)
            childFragmentManager.beginTransaction()
                .replace(R.id.detail_frame, mealDetailFragment)
                .commit()
        }
        else {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("meal", meal)
            startActivity(intent)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(mealList: ArrayList<Meal>) = MealsGridFragment().apply {
            arguments = Bundle().apply {
                putSerializable("mealList", mealList)
            }
        }
    }

}