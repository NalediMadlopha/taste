package com.taste.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taste.app.R
import com.taste.app.model.Category
import kotlinx.android.synthetic.main.list_item_category.view.*


class CategoryListAdapter(private var categories: List<Category>, private val displayCategoryMeals: (category: String) -> Unit) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_category, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], displayCategoryMeals)
    }

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (category: Category, displayCategoryMeals: (category: String) -> Unit) {
            itemView.categoryNameTextView.text = category.strCategory
            itemView.categoryDescriptionTextView.text = category.strCategoryDescription

            Glide.with(itemView.context)
                .load(category.strCategoryThumb)
                .into(itemView.categoryImageView);

            itemView.setOnClickListener {
                displayCategoryMeals(category.strCategory)
            }
        }
    }
}