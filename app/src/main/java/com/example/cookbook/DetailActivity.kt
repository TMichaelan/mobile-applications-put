package com.example.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookbook.models.Meal

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            val meal = intent.getParcelableExtra<Meal>("meal")
            if (meal != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MealDetailFragment.newInstance(meal))
                    .commit()
            } else {
                // Закройте активность или покажите сообщение об ошибке
                Toast.makeText(this, "Error: Meal not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
