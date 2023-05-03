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
                // # Home Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Music Fragment")
                val homeFragment = HomeFragment()
                homeFragment.arguments = bundle
                return homeFragment
            }
            1 -> {
                // # Movies Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Movies Fragment")
                val moviesFragment = TimerFragment()
                moviesFragment.arguments = bundle
                return moviesFragment
            }
//                2 -> {
//                    // # Books Fragment
//                    val bundle = Bundle()
//                    bundle.putString("fragmentName", "Books Fragment")
//                    val booksFragment = DemoFragment()
//                    booksFragment.arguments = bundle
//                    return booksFragment
//                }
            3->{
                val bundle = Bundle()
                bundle.putString("fragmentName", "Test Fragment")
                val moviesFragment = TestFragment()
                moviesFragment.arguments = bundle
                return TestFragment()
            }

            else -> return HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
