package com.taste.app.di

import com.taste.app.AppExecutors
import com.taste.app.api.TheMealDbService
import com.taste.app.database.CategoryDao
import com.taste.app.database.MealDao
import com.taste.app.repo.MealRepository
import com.taste.app.repo.MealRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMealRepository(appExecutors: AppExecutors, categoryDao: CategoryDao,
                              mealDao: MealDao, service: TheMealDbService
    ): MealRepository {
        return MealRepositoryImpl(appExecutors, categoryDao, mealDao, service)
    }

}