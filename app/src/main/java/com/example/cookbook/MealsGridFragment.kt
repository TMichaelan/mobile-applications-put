package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.data.retrofit.RetrofitInstance
import com.example.cookbook.models.Meal
import com.example.cookbook.models.MealList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response

class MealsGridFragment : Fragment(), MealAdapter.OnItemClickListener {

    private var selectedMeal: Meal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutId = if (resources.getBoolean(R.bool.is_tablet)) R.layout.meals_grid_and_detail else R.layout.fragment_meals_grid
        return inflater.inflate(layoutId, container, false)
    }
    private fun getRandomMeal(callback: (ArrayList<Meal>) -> Unit) {
        val mealList = ArrayList<Meal>()
        var count = 0
        val randomMealAmount = 20
        for (i in 1..randomMealAmount) {
            RetrofitInstance.mealAPI.getRandomMeal().enqueue(object : retrofit2.Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.body() != null) {
                        val randomMeal: Meal = response.body()!!.meals[0]
                        mealList += randomMeal
//                        Log.d("MyLog", "meal ${randomMeal.idMeal} name ${randomMeal.strMeal} mealList${mealList}")
                    }
                    count++
                    if (count == randomMealAmount) {
                        callback(mealList)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
//                    Log.d("MyLog", "error: ${t.message.toString()}")
                    count++
                    if (count == randomMealAmount) {
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
            val itemAdapter = MealAdapter(mealList, this)
            // Set the LayoutManager that
            // this RecyclerView will use.
            val recyclerView: RecyclerView =view.findViewById(R.id.recycleView)
            var columns = 2
            if (resources.getBoolean(R.bool.is_tablet)) columns = 1
            recyclerView.layoutManager = GridLayoutManager(context, columns)
            // adapter instance is set to the
            // recyclerview to inflate the items.
            recyclerView.adapter = itemAdapter
        }


    }

    private fun shareIngredients() {
        val meal = selectedMeal
        if (meal == null) {
            Toast.makeText(requireContext(), "Выберите блюдо для отправки списка ингредиентов", Toast.LENGTH_SHORT).show()
            return
        }

        val ingredients = meal.getIngredientsList()
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, ingredients.joinToString(", "))
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Отправить ингредиенты"))
    }

    override fun onItemClick(meal: Meal) {
        // Здесь вы можете открыть MealDetailActivity и передать информацию о выбранном блюде

        selectedMeal = meal

        if (resources.getBoolean(R.bool.is_tablet)) {
            val text = view?.findViewById<TextView>(R.id.textView)
            if (text != null) {
                text.visibility = View.GONE
            }

            val mealDetailFragment = MealDetailFragment.newInstance(meal)
            parentFragmentManager.beginTransaction()
                .replace(R.id.detail_frame, mealDetailFragment)
                .commit()
        }
        else{
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("meal", meal)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MealsGridFragment()
    }
}