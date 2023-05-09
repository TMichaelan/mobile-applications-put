package com.example.cookbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Home Fragment")
                val homeFragment = HomeFragment()
                homeFragment.arguments = bundle
                return homeFragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Meals Grid Fragment")
                val mealsGridFragment = MealsGridFragment()
                mealsGridFragment.arguments = bundle
                return mealsGridFragment

            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Favorites Fragment")
                val savedMealsFragment = SavedMealsFragment()
                savedMealsFragment.arguments = bundle
                return savedMealsFragment
            }

            else -> return HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
