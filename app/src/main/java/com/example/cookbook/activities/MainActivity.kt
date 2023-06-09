package com.example.cookbook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.R
import com.example.cookbook.adapters.TabsPagerAdapter
import com.example.cookbook.databinding.ActivityMainBinding
import com.example.cookbook.models.Meal
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение списка блюд, переданных из SplashActivity
        val mealList = intent.getSerializableExtra("mealList") as ArrayList<Meal>

        // Number Of Tabs
        val numberOfTabs = 3

        // Set the ViewPager Adapter
        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs, mealList)
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
