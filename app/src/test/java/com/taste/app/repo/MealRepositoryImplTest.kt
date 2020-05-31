package com.taste.app.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.taste.app.api.CategoryResponse
import com.taste.app.api.MealResponse
import com.taste.app.api.TheMealDbService
import com.taste.app.database.CategoryDao
import com.taste.app.database.MealDao
import com.taste.app.model.Category
import com.taste.app.model.Meal
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Call
import retrofit2.Response
import util.InstantAppExecutors
import util.dummyCategories
import util.dummyMeals

class MealRepositoryImplTest {

    private lateinit var repository: MealRepository

    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var mockCategoryDao: CategoryDao
    @Mock
    private lateinit var mockMealDao: MealDao
    @Mock
    private lateinit var mockService: TheMealDbService
    @Mock
    private lateinit var mockCategoryResponseCall: Call<CategoryResponse>
    @Mock
    private lateinit var mockMealResponseCall: Call<MealResponse>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        initMocks(this)
        mockWebServer = MockWebServer()
        repository = MealRepositoryImpl(InstantAppExecutors(), mockCategoryDao, mockMealDao, mockService)
    }

    @Test
    fun `fetchCategories should invoke the fetch category service call`() {
        val successResponse = Response.success(CategoryResponse(arrayListOf()))

        `when`(mockService.fetchCategories()).thenReturn(mockCategoryResponseCall)
        `when`(mockCategoryResponseCall.execute()).thenReturn(successResponse)

        repository.fetchCategories()

        verify(mockService).fetchCategories()
    }

    @Test
    fun `saveCategories should save the categories to the database`() {
        val expectedCategories = MutableLiveData(dummyCategories())

        `when`(mockCategoryDao.getAll()).thenReturn(expectedCategories)

        repository.saveCategories(expectedCategories.value!!)

        verify(mockCategoryDao).insertAll(expectedCategories.value!!)
        assertEquals(expectedCategories.value, mockCategoryDao.getAll().value)
    }

    @Test
    fun `getCategories should invoke the category dao getAll`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()

        `when`(mockCategoryDao.getAll()).thenReturn(expectedCategories)

        val categories = repository.getCategories()

        verify(mockCategoryDao).getAll()
        assertEquals(expectedCategories, categories)
    }

    @Test
    fun `fetchMeals should invoke the fetch meals service call`() {
        val category = "52772"
        val successResponse = Response.success(MealResponse(arrayListOf()))

        `when`(mockService.fetchMeals(category)).thenReturn(mockMealResponseCall)
        `when`(mockMealResponseCall.execute()).thenReturn(successResponse)

        repository.fetchMeals(category)

        verify(mockService).fetchMeals(category)
    }

    @Test
    fun `saveMeals should save the meals to the database`() {
        val expectedMeals = MutableLiveData(dummyMeals())

        `when`(mockMealDao.getAll()).thenReturn(expectedMeals)

        repository.saveMeals(expectedMeals.value!!)

        verify(mockMealDao).insertAll(expectedMeals.value!!)
        assertEquals(expectedMeals.value, mockMealDao.getAll().value)
    }

    @Test
    fun `getMealsByCategory should invoke the meals dao getMealsByCategory`() {
        val category = "52772"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()

        `when`(mockMealDao.getMealsByCategory(category)).thenReturn(expectedMeals)

        val meals = repository.getMeals(category)

        verify(mockMealDao).getMealsByCategory(category)
        assertEquals(expectedMeals, meals)
    }

}