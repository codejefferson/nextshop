package com.nextshop.repository

import com.nextshop.service.api.BaseAPI
import com.nextshop.service.model.ProductItemResponse

object ProductsRepository : BaseRepository() {

    suspend fun getProducts(tag: String): List<ProductItemResponse> {
        val response = safeApiCall(
            call = {
                BaseAPI.baseService.getProductsAsync(
                    tag = tag
                ).await()
            },
            errorMessage = "Error to fetching images"
        )

        val result: MutableList<ProductItemResponse> = mutableListOf()
        response?.products?.map { it: ProductItemResponse -> result.add(it) }

        return result
    }

}