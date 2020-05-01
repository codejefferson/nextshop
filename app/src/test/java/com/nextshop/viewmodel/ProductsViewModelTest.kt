package com.nextshop.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nextshop.repository.ProductsRepository
import com.nextshop.service.model.ProductsItemResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class ProductsViewModelTest {

    @MockK
    lateinit var repository: ProductsRepository
    lateinit var viewModel: ProductsViewModel
    val dispatcher = Dispatchers.Unconfined

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ProductsViewModel(dispatcher, dispatcher)
    }

    @Test
    fun test_fetchProduct() {
        coEvery {
            repository.getProducts(any())
        } returns listOf(
            ProductsItemResponse("66666", "teste", "teste", "teste"),
            ProductsItemResponse("77777", "teste", "teste", "teste"),
            ProductsItemResponse("88888", "teste", "teste", "teste"),
            ProductsItemResponse("99999", "teste", "teste", "teste")
        )
        viewModel.products.observeForever {}
        viewModel.fetchProducts("apple")

        assert(viewModel.products.value != null)
    }

    @Test
    fun test_fetchProduct_Empty() {
        coEvery {
            repository.getProducts(any())
        } coAnswers (
            throw Exception("No network")
        )

        viewModel.products.observeForever {}
        viewModel.fetchProducts("apple")

        assert(viewModel.products.value != null)
        assert(viewModel.products.value?.size == 0)
    }
}