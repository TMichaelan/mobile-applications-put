package com.example.cookbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cookbook.databinding.FragmentHomeBinding
import com.example.cookbook.models.Meal
import com.example.cookbook.models.MealList
import com.example.easyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        RetrofitInstance.mealAPI.getRandomMeal().enqueue(object : Callback<MealList>{
//            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
//                if(response.body() != null){
//                    val randomMeal: Meal = response.body()!!.meals[0]
//
//
////                    Glide.with(this@HomeFragment)
////                        .load(randomMeal.strMealThumb)
////                        .into(binding.imageRandomMeal)
//
//                    Log.d("MyLog", "meal ${randomMeal.idMeal} name ${randomMeal.strMeal}")
//                }else{
//                    return
//                }
//            }
//
//            override fun onFailure(call: Call<MealList>, t: Throwable) {
//                Log.d("MyLog", "error: ${t.message.toString()}")
//            }
//        })
//    }

}