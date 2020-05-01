package com.nextshop.viewmodel

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextshop.repository.ProductRepository
import com.nextshop.service.model.ProductDetailResponse
import kotlinx.coroutines.*

class ProductViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val product = MutableLiveData<ProductDetailResponse>()

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispatcher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    @UiThread
    fun fetchProduct(productId: String): LiveData<ProductDetailResponse> {
        uiScope.launch {
            try {
                val data = ioScope.async {
                    return@async ProductRepository.getProduct(productId)
                }.await()

                product.value = data?.product
            } catch (e: Exception) {
                product.value = null
            }
        }
        return product
    }

}