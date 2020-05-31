package com.taste.app.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import util.dummyCategories
import util.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest : TasteDatabaseTest() {

    private lateinit var categoryDao: CategoryDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        super.setUp()
        categoryDao = database.categoryDao()
    }

    @Test
    fun insertAll_should_insert_a_list_of_categories_to_the_database() {
        database.categoryDao().insertAll(dummyCategories())

        assertNotNull(database.categoryDao().getAll().getOrAwaitValue())
    }

    @Test
    fun getAll_should_return_a_list_of_categories_from_the_database() {
        database.categoryDao().insertAll(dummyCategories())

        val categories = database.categoryDao().getAll().getOrAwaitValue()

        assertThat(categories.size, equalTo(2))
    }

    @Test
    fun findById_should_return_a_specific_category_from_the_database() {
        database.categoryDao().insertAll(dummyCategories())

        val category = database.categoryDao().findById("2").getOrAwaitValue()

        assertThat(category, equalTo(dummyCategories()[1]))
    }

}