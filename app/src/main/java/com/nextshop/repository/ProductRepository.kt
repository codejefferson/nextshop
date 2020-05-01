package com.nextshop.repository

import com.nextshop.service.api.BaseAPI
import com.nextshop.service.model.ProductResponse

object ProductRepository : BaseRepository() {

    suspend fun getProduct(productId: String): ProductResponse? {
        val response = safeApiCall(
            call = {
                BaseAPI.baseService.getProductAsync(
                    productId = productId
                ).await()
            },
            errorMessage = "Error to fetching image"
        )
        return response
    }
}