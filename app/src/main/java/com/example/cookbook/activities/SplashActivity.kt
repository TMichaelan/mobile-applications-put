package com.example.cookbook.activities

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.example.cookbook.models.MealRepository
import com.example.cookbook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoImageView = findViewById<ImageView>(R.id.logo_image_view)

        // Анимация с использованием ObjectAnimator
        val rotationAnimator = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 360f)
        rotationAnimator.duration = 4000
        rotationAnimator.interpolator = DecelerateInterpolator()

        rotationAnimator.start()

        // Задержка перед переходом на главный экран
        CoroutineScope(Dispatchers.Main).launch {
            MealRepository.getRandomMeal { mealList ->

                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.putExtra("mealList", mealList)
                startActivity(intent)

                finish()
            }
        }
    }
}
