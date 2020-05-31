package com.taste.app.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.taste.app.api.MealResponse
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