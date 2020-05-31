package com.taste.app.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.taste.app.api.CategoryResponse
import com.taste.app.api.MealResponse
import com.taste.app.model.Category
import com.taste.app.model.Meal
import com.taste.app.repo.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Response
import util.InstantAppExecutors
import util.TestCoroutineRule
import util.dummyCategories
import util.dummyMeals


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MealViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MealViewModel

    @Mock
    private lateinit var mockRepository: MealRepository

    @Before
    fun setUp() {
        initMocks(this)
        viewModel = MealViewModel(mockRepository, InstantAppExecutors(), Dispatchers.Unconfined)
    }

    @Test
    fun `fetchCategories should not save the categories in the database if the response is not successful`() {
        val categories = dummyCategories()
        val response = Response.error<CategoryResponse>(400, "".toResponseBody("text/plain".toMediaTypeOrNull()))

        `when`(mockRepository.fetchCategories()).thenReturn(response)

        viewModel.fetchCategories()

        verify(mockRepository, never()).saveCategories(categories)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchCategories should set an error state if the response is not successful`() {
        val response = Response.error<CategoryResponse>(400, "".toResponseBody("text/plain".toMediaTypeOrNull()))

        `when`(mockRepository.fetchCategories()).thenReturn(response)

        viewModel.fetchCategories()

        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchCategories should set an error state if the response is successful with null body`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()
        val response = Response.success(null)

        `when`(mockRepository.fetchCategories()).thenReturn(response as Response<CategoryResponse>)
        `when`(mockRepository.getCategories()).thenReturn(expectedCategories)

        viewModel.fetchCategories()

        verify(mockRepository, never()).saveCategories(expectedCategories.value!!)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchCategories should set an error state if the response is successful with null category list`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()
        val response = Response.success(CategoryResponse(null))

        `when`(mockRepository.fetchCategories()).thenReturn(response)
        `when`(mockRepository.getCategories()).thenReturn(expectedCategories)

        viewModel.fetchCategories()

        verify(mockRepository, never()).saveCategories(expectedCategories.value!!)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchCategories should set an error state if the response is successful with an empty category list`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()
        val response = Response.success(CategoryResponse(emptyList()))

        `when`(mockRepository.fetchCategories()).thenReturn(response)
        `when`(mockRepository.getCategories()).thenReturn(expectedCategories)

        viewModel.fetchCategories()

        verify(mockRepository, never()).saveCategories(expectedCategories.value!!)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchCategories should save the categories to the database if the response is successful`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()
        val response = Response.success(CategoryResponse(expectedCategories.value!!))

        `when`(mockRepository.fetchCategories()).thenReturn(response)
        `when`(mockRepository.getCategories()).thenReturn(expectedCategories)

        viewModel.fetchCategories()

        verify(mockRepository).saveCategories(expectedCategories.value!!)
        assertEquals(expectedCategories, mockRepository.getCategories())
    }

    @Test
    fun `fetchCategories should set the success state if the response is successful`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()
        val response = Response.success(CategoryResponse(expectedCategories.value!!))

        `when`(mockRepository.fetchCategories()).thenReturn(response)
        `when`(mockRepository.getCategories()).thenReturn(expectedCategories)

        viewModel.fetchCategories()

        assertTrue(viewModel.isSuccess().value!!)
    }

    @Test
    fun `getCategories should return categories from the database`() {
        val expectedCategories = MutableLiveData<List<Category>>()
        expectedCategories.value = dummyCategories()

        `when`(mockRepository.getCategories()).thenReturn(expectedCategories)

        val categories = viewModel.getCategories()

        assertEquals(expectedCategories, categories)
    }

    @Test
    fun `fetchMeals should not save the meals in the database if the response is not successful`() {
        val category = "Sea Food"
        val meals = dummyMeals()
        val response = Response.error<MealResponse>(400, "".toResponseBody("text/plain".toMediaTypeOrNull()))

        `when`(mockRepository.fetchMeals(category)).thenReturn(response)

        viewModel.fetchMeals(category)

        verify(mockRepository, never()).saveMeals(meals)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchMeals should set an error state if the response is not successful`() {
        val category = "Sea Food"
        val response = Response.error<MealResponse>(400, "".toResponseBody("text/plain".toMediaTypeOrNull()))

        `when`(mockRepository.fetchMeals(category)).thenReturn(response)

        viewModel.fetchMeals(category)

        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchMeals should set an error state if the response is successful with null body`() {
        val category = "Sea Food"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()
        val response = Response.success(null)

        `when`(mockRepository.fetchMeals(category)).thenReturn(response as Response<MealResponse>)
        `when`(mockRepository.getMeals(category)).thenReturn(expectedMeals)

        viewModel.fetchMeals(category)

        verify(mockRepository, never()).saveMeals(expectedMeals.value!!)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchMeals should set an error state if the response is successful with null meal list`() {
        val category = "Sea Food"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()
        val response = Response.success(MealResponse(null))

        `when`(mockRepository.fetchMeals(category)).thenReturn(response as Response<MealResponse>)
        `when`(mockRepository.getMeals(category)).thenReturn(expectedMeals)

        viewModel.fetchMeals(category)

        verify(mockRepository, never()).saveMeals(expectedMeals.value!!)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchMeals should set an error state if the response is successful with an empty meal list`() {
        val category = "Sea Food"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()
        val response = Response.success(MealResponse(emptyList()))

        `when`(mockRepository.fetchMeals(category)).thenReturn(response as Response<MealResponse>)
        `when`(mockRepository.getMeals(category)).thenReturn(expectedMeals)

        viewModel.fetchMeals(category)

        verify(mockRepository, never()).saveMeals(expectedMeals.value!!)
        assertTrue(viewModel.isError().value!!)
    }

    @Test
    fun `fetchMeals should save the meals to the database if the response is successful`() {
        val category = "Sea Food"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()
        val response = Response.success(MealResponse(expectedMeals.value!!))

        `when`(mockRepository.fetchMeals(category)).thenReturn(response)
        `when`(mockRepository.getMeals(category)).thenReturn(expectedMeals)

        viewModel.fetchMeals(category)

        verify(mockRepository).saveMeals(expectedMeals.value!!)
        assertEquals(expectedMeals, mockRepository.getMeals(category))
    }

    @Test
    fun `fetchMeals should set the success state if the response is successful`() {
        val category = "Sea Food"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()
        val response = Response.success(MealResponse(expectedMeals.value!!))

        `when`(mockRepository.fetchMeals(category)).thenReturn(response)
        `when`(mockRepository.getMeals(category)).thenReturn(expectedMeals)

        viewModel.fetchMeals(category)

        assertTrue(viewModel.isSuccess().value!!)
    }

    @Test
    fun `getMeals should return meals from the database`() {
        val category = "Sea Food"
        val expectedMeals = MutableLiveData<List<Meal>>()
        expectedMeals.value = dummyMeals()

        `when`(mockRepository.getMeals(category)).thenReturn(expectedMeals)

        val meals = viewModel.getMeals(category)

        assertEquals(expectedMeals, meals)
    }

}