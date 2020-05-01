package com.nextshop.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nextshop.repository.ProductRepository
import com.nextshop.repository.ProductsRepository
import com.nextshop.service.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class ProductViewModelTest {

    @MockK
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductViewModel
    private val dispatcher = Dispatchers.Unconfined

    @get:Rule
    private var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ProductViewModel(dispatcher, dispatcher)
    }

    @Test
    fun test_fetchProduct() {
        coEvery {
            repository.getProduct(any())
        } returns
            ProductResponse(ProductDetailResponse(
                "3333",
                "item3333",
                "3333",
                3333,
                listOf(),
                listOf(),
                listOf(),
                ReviewInformationResponse(ReviewSummary(2.2, 333))))

        viewModel.product.observeForever {}
        viewModel.fetchProduct("3333")

        assert(viewModel.product.value != null)
    }

    @Test
    fun test_fetchProduct_Null() {
        coEvery {
            repository.getProduct(any())
        } coAnswers (
            throw Exception("No network")
        )

        viewModel.product.observeForever {}
        viewModel.fetchProduct("44444")

        assert(viewModel.product.value == null)
    }
}