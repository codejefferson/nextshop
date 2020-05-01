package com.nextshop.service.model

data class ProductResponse(val product: ProductDetailResponse)

data class ProductDetailResponse(
    val productId: String,
    val productName: String,
    val productText: String,
    val salesPriceIncVat: Int,
    val productImages: List<String>,
    val pros: List<String>,
    val cons: List<String>,
    val reviewInformation: ReviewInformationResponse
)

data class ReviewInformationResponse(val reviewSummary: ReviewSummary)

data class ReviewSummary(
    val reviewAverage: Double,
    val reviewCount: Int
)