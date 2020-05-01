package com.nextshop.service.api

import com.nextshop.service.model.ProductResponse
import com.nextshop.service.model.ProductsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BaseService {

    @GET("search?")
    fun getProductsAsync(@Query("tag") tag: String): Deferred<Response<ProductsResponse>>

    @GET("product/{productId}")
    fun getProductAsync(@Path("productId") productId: String): Deferred<Response<ProductResponse>>

}