package com.taste.app.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.taste.app.R
import com.taste.app.di.Injectable
import com.taste.app.util.showSnackBar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_category_list.*
import javax.inject.Inject


class CategoryListActivity : AppCompatActivity(), Injectable, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CategoryViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        categoryListSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.design_default_color_secondary))
        categoryListSwipeRefreshLayout.setOnRefreshListener(this)

        adapter = CategoryListAdapter(emptyList(), this@CategoryListActivity::displayCategoryMeals)
        categoryListRecyclerView.adapter = adapter

        observeLoadingState()
        observeSuccessState()
        observeErrorState()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.getCategories().observe(this, Observer {
            adapter.updateCategories(it)
        })
    }

    private fun observeLoadingState() {
        viewModel.isLoading().observe(this, Observer {
            categoryListSwipeRefreshLayout.isRefreshing = it;
        })
    }

    private fun observeSuccessState() {
        viewModel.isSuccess().observe(this, Observer { success ->
            if (success) {
                showSnackBar(
                    category_list_layout,
                    R.string.message_successful_update,
                    R.color.black,
                    R.color.design_default_color_secondary
                )
            }
        })
    }

    private fun observeErrorState() {
        viewModel.isError().observe(this, Observer { error ->
            if (error) {
                showSnackBar(
                    category_list_layout,
                    R.string.message_unsuccessful_update,
                    R.color.white,
                    android.R.color.holo_red_dark
                )
            }
        })
    }

    override fun onRefresh() {
        viewModel.fetchCategories()
    }

    private fun displayCategoryMeals(category: String) {
        startActivity(MealListActivity.createIntent(this, category))
    }

}