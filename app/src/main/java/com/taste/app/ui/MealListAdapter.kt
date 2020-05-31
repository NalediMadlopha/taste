package com.taste.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taste.app.R
import com.taste.app.model.Meal
import kotlinx.android.synthetic.main.list_item_meal.view.*


class MealListAdapter(private var meals: List<Meal>) : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_meal, parent, false)

        return MealViewHolder(view)
    }

    override fun getItemCount() = meals.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    fun updateMeals(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (meal: Meal) {
            itemView.mealNameTextView.text = meal.strMeal

            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .into(itemView.mealImageView);

        }
    }

}