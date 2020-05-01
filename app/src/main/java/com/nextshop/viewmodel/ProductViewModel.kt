package com.nextshop.viewmodel

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextshop.repository.ProductRepository
import com.nextshop.service.model.ProductDetailResponse
import kotlinx.coroutines.*
import java.lang.NullPointerException

class ProductViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val product = MutableLiveData<LiveDataResult<ProductDetailResponse>>()

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispatcher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    @UiThread
    fun fetchProduct(productId: String): LiveData<LiveDataResult<ProductDetailResponse>> {
        uiScope.launch {
            try {
                val data = ioScope.async {
                    return@async ProductRepository.getProduct(productId)
                }.await()

               data?.let {
                    product.value = LiveDataResult.success(data.product)
                }
            } catch (e: Exception) {
                product.value = null
            }
        }
        return product
    }

}