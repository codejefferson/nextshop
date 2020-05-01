package com.nextshop.repository

import com.nextshop.service.model.*
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
class ProductRepositoryTest {

    @MockK
    lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test_getProducts_NotEmpty() = runBlocking {
        coEvery { repository.getProduct(any()) } returns ProductResponse(
            ProductDetailResponse(
                "3333",
                "3333",
                "3333",
                3333,
                listOf(),
                listOf(),
                listOf(),
                ReviewInformationResponse(
                    ReviewSummary(2.2, 333)
                )
            )
        )

        val item = repository.getProduct("3333")
        assertNotNull(item)
    }

    @Test
    fun test_getProducts_Empty() = runBlocking {
        coEvery { repository.getProduct(any()) } returns null

        val item = repository.getProduct("3333")
        assertNull(item)
    }

    @Test
    fun test_getProducts_ValidContent() = runBlocking {
        coEvery { repository.getProduct(any()) } returns ProductResponse(
            ProductDetailResponse(
                "3333",
                "item3333",
                "3333",
                3333,
                listOf(),
                listOf(),
                listOf(),
                ReviewInformationResponse(
                    ReviewSummary(2.2, 333)
                )
            )
        )

        val item = repository.getProduct("3333")
        assertNotNull(item)

        assertEquals("3333", item?.product?.productId)
        assertEquals("item3333", item?.product?.productName)
        assertEquals(3333, item?.product?.salesPriceIncVat)
    }
}