package com.nextshop.repository

import com.nextshop.service.api.BaseAPI
import com.nextshop.service.model.ProductsItemResponse

object ProductsRepository : BaseRepository() {

    suspend fun getProducts(tag: String): List<ProductsItemResponse> {
        val response = safeApiCall(
            call = {
                BaseAPI.baseService.getProductsAsync(
                    tag = tag
                ).await()
            },
            errorMessage = "Error to fetching images"
        )

        val result: MutableList<ProductsItemResponse> = mutableListOf()
        response?.products?.map { it: ProductsItemResponse -> result.add(it) }

        return result
    }

}