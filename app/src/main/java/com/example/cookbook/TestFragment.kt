package com.example.cookbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cookbook.databinding.FragmentHomeBinding
import com.example.cookbook.databinding.FragmentTestBinding
import com.example.cookbook.models.Category
import com.example.cookbook.models.CategoryList
import com.example.cookbook.models.Meal
import com.example.cookbook.models.MealList
import com.example.easyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTestBinding
    private var categories: MutableLiveData<List<Category>> = MutableLiveData<List<Category>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getRandomMeal()

    }
    override fun onResume() {
        super.onResume()
//        getRandomMeal()
        observeCategories()
    }
    private fun getRandomMeal() {
        RetrofitInstance.mealAPI.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]

                    Glide.with(this@TestFragment)
                        .load(randomMeal.strMealThumb)
                        .into(binding.imgRandomMeal)

                    Log.d("MyLog", "meal ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MyLog", "error: ${t.message.toString()}")
            }
        })
    }

    private fun getCategories(){
        RetrofitInstance.mealAPI.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categories.value = response.body()!!.categories

                    Log.d("MyLog", "cat ${categories.value}")

                }
                else{
                    return
                }


            }
            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("MyLog", "error: ${t.message.toString()}")
            }

        })
    }

    fun observeCategories(): LiveData<List<Category>> {
        return categories
    }

//    private fun observeCategoriess() {
//        observeCategories().observe(viewLifecycleOwner,object :
//            Observer<List<Category>> {
//
//            override fun onChanged(t: List<Category>?) {
//                myAdapter.setCategoryList(t!!)
//            }
//
//        })
//    }


}