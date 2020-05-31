package com.taste.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.taste.app.R
import com.taste.app.di.Injectable
import com.taste.app.util.showSnackBar
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MealViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: CategoryListAdapter
    private lateinit var categoryListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryListSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.design_default_color_secondary))
        categoryListSwipeRefreshLayout.setOnRefreshListener(this)

        adapter = CategoryListAdapter(emptyList(), this@MainActivity::displayCategoryMeals)
        categoryListRecyclerView = findViewById(R.id.categoryListRecyclerView)
        categoryListRecyclerView.adapter = adapter

        observeCategories()
        observeLoadingState()
        observeSuccessState()
        observeErrorState()
    }

    private fun observeCategories() {
        viewModel.getCategories().observe(this, Observer {
            adapter.updateCategories(it)
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

    private fun observeLoadingState() {
        viewModel.isLoading().observe(this, Observer {
            categoryListSwipeRefreshLayout.isRefreshing = it;
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
        Toast.makeText(this, category, Toast.LENGTH_LONG).show()
    }
}