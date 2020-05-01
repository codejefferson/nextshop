package com.nextshop.service.model

data class ProductsResponse(val products: List<ProductsItemResponse>)

data class ProductsItemResponse(val productId: String,
                                val productName: String,
                                val salesPriceIncVat: String,
                                val productImage: String)