package com.taste.app.di

import android.app.Application
import androidx.room.Room
import com.taste.app.api.TheMealDbService
import com.taste.app.database.CategoryDao
import com.taste.app.database.MealDao
import com.taste.app.database.TasteDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule() {

    @Singleton
    @Provides
    fun provideTheMealDbService(): TheMealDbService {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMealDbService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): TasteDatabase {
        return Room
            .databaseBuilder(app, TasteDatabase::class.java, "taste.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: TasteDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideMealDao(database: TasteDatabase): MealDao {
        return database.mealDao()
    }

}