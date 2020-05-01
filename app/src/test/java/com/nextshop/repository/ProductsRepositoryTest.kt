package com.nextshop.repository

import com.nextshop.service.model.ProductsItemResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProductsRepositoryTest {

    @MockK
    lateinit var repository: ProductsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test_getProducts_NotEmpty() = runBlocking {
        coEvery { repository.getProducts("apple") } returns listOf(
            ProductsItemResponse("777", "teste777", "777", "teste777777"),
            ProductsItemResponse("432432", "teste432432", "432432", "teste432432432432"),
            ProductsItemResponse("6665435666", "teste6665435666", "6665435666", "teste66654356666665435666")
        )

        val list = repository.getProducts("apple")
        assertNotNull(list)
    }

    @Test
    fun test_getProducts_Empty() = runBlocking {
        coEvery { repository.getProducts("apple") } returns emptyList()

        val list = repository.getProducts("apple")
        assert(list.isEmpty())
    }

    @Test
    fun test_getProducts_ValidContent() = runBlocking {
        coEvery { repository.getProducts("apple") } returns listOf(
            ProductsItemResponse("777", "teste777", "777", "teste777777"),
            ProductsItemResponse("432432", "teste432432", "432432", "teste432432432432"),
            ProductsItemResponse("6665435666", "teste6665435666", "6665435666", "teste66654356666665435666")
        )

        val list = repository.getProducts("apple")
        assertNotNull(list)

        assertEquals("777", list[0].productId)
        assertEquals("teste777", list[0].productName)
        assertEquals("teste432432432432", list[1].productImage)
        assertEquals("432432", list[1].salesPriceIncVat)
    }
}