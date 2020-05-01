package com.nextshop.service.model

data class ProductsResponse(val products: List<ProductItemResponse>)

data class ProductItemResponse(val productId: String,
                               val productName: String,
                               val salesPriceIncVat: String,
                               val productImage: String)