package com.taste.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.taste.app.R
import com.taste.app.di.Injectable
import com.taste.app.util.showSnackBar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_meal_list.*
import javax.inject.Inject


private const val SELECTED_CATEGORY = "selected_category"

class MealListActivity : AppCompatActivity(), Injectable, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MealViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: MealListAdapter
    private lateinit var selectedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_list)
        setupToolbar()

        selectedCategory = intent.getStringExtra(SELECTED_CATEGORY).orEmpty()

        mealListSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.design_default_color_secondary))
        mealListSwipeRefreshLayout.setOnRefreshListener(this)

        adapter = MealListAdapter(emptyList())
        mealListRecyclerView.adapter = adapter

        observeLoadingState()
        observeSuccessState()
        observeErrorState()
        observeMeals()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        toolbar.title = intent.getStringExtra(SELECTED_CATEGORY)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeMeals() {
        viewModel.getMeals(selectedCategory).observe(this, Observer {
            if (it.isNullOrEmpty()) {
                viewModel.fetchMeals(selectedCategory)
            } else {
                adapter.updateMeals(it)
            }
        })
    }

    private fun observeLoadingState() {
        viewModel.isLoading().observe(this, Observer {
            mealListSwipeRefreshLayout.isRefreshing = it;
        })
    }

    private fun observeSuccessState() {
        viewModel.isSuccess().observe(this, Observer { success ->
            if (success) {
                showSnackBar(
                    meal_list_layout,
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
                    meal_list_layout,
                    R.string.message_unsuccessful_update,
                    R.color.white,
                    android.R.color.holo_red_dark
                )
            }
        })
    }

    companion object {
        fun createIntent(context: Context, category: String): Intent {
            return Intent(context, MealListActivity::class.java).apply {
                putExtra(SELECTED_CATEGORY, category)
            }
        }
    }

    override fun onRefresh() {
        viewModel.fetchMeals(selectedCategory)
    }

}