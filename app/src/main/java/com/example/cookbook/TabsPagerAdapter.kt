package com.example.cookbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookbook.models.Meal

class TabsPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private var numberOfTabs: Int,
    private val mealList: ArrayList<Meal>
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Home Fragment")
                val homeFragment = HomeFragment()
                homeFragment.arguments = bundle
                homeFragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Meals Grid Fragment")
                bundle.putSerializable("mealList", mealList)
                val mealsGridFragment = MealsGridFragment()
                mealsGridFragment.arguments = bundle
                mealsGridFragment
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Favorites Fragment")
                val savedMealsFragment = SavedMealsFragment()
                savedMealsFragment.arguments = bundle
                savedMealsFragment
            }
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
