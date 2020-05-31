package com.taste.app.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.taste.app.api.CategoryResponse
import com.taste.app.model.Category
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


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CategoryViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CategoryViewModel

    @Mock
    private lateinit var mockRepository: MealRepository

    @Before
    fun setUp() {
        initMocks(this)
        viewModel = CategoryViewModel(mockRepository, InstantAppExecutors(), Dispatchers.Unconfined)
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

}