package com.example.cookbook

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.ActivityDetailBinding
import com.example.cookbook.databinding.ActivityMainBinding
import com.example.cookbook.data.retrofit.RetrofitInstance
import com.example.cookbook.models.Meal
import com.example.cookbook.retrofit.MealAPI
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Number Of Tabs
        val numberOfTabs = 3

        // Set the ViewPager Adapter
        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        binding.tabsViewpager.adapter = adapter

        // Enable Swipe
        binding.tabsViewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.setIcon(R.drawable.ic_home_round)
                }
                1 -> {
                    tab.text = "Recipes"
                    tab.setIcon(R.drawable.ic_recipe_round)
                }
                2 -> {
                    tab.text = "Favorites"
                    tab.setIcon(R.drawable.ic_favorites_round)
                }
            }
        }.attach()
    }
}

