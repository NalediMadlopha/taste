package com.taste.app.di

import com.taste.app.ui.CategoryListActivity
import com.taste.app.ui.MealListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeCategoryListActivity(): CategoryListActivity

    @ContributesAndroidInjector
    abstract fun contributeMealListActivity(): MealListActivity

}