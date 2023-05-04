package com.example.cookbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.data.retrofit.RetrofitInstance
import com.example.cookbook.models.Meal
import com.example.cookbook.models.MealList
import retrofit2.Call
import retrofit2.Response

class MealsGridFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_meals_grid, container, false)
    }

    private fun getRandomMeal(callback: (ArrayList<Meal>) -> Unit) {
        val mealList = ArrayList<Meal>()
        var count = 0
        for (i in 1..50) {
            RetrofitInstance.mealAPI.getRandomMeal().enqueue(object : retrofit2.Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.body() != null) {
                        val randomMeal: Meal = response.body()!!.meals[0]
                        mealList += randomMeal
                        Log.d("MyLog", "meal ${randomMeal.idMeal} name ${randomMeal.strMeal} mealList${mealList}")
                    }
                    count++
                    if (count == 50) {
                        callback(mealList)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("MyLog", "error: ${t.message.toString()}")
                    count++
                    if (count == 50) {
                        callback(mealList)
                    }
                }
            })
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getting the mealList
        getRandomMeal{mealList ->
            // Assign mealList to ItemAdapter
            val itemAdapter= MealAdapter(mealList)
            // Set the LayoutManager that
            // this RecyclerView will use.
            val recyclerView: RecyclerView =view.findViewById(R.id.recycleView)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            // adapter instance is set to the
            // recyclerview to inflate the items.
            recyclerView.adapter = itemAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MealsGridFragment()
    }
}