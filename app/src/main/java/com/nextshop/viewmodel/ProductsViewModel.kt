package com.nextshop.viewmodel

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextshop.mechanism.SimpleCoroutines
import com.nextshop.repository.ProductsRepository
import com.nextshop.service.model.ProductsItemResponse
import kotlinx.coroutines.*

class ProductsViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val products = MutableLiveData<List<ProductsItemResponse>>()

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispatcher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    @UiThread
    fun fetchProducts(tag: String): LiveData<List<ProductsItemResponse>> {
        uiScope.launch {
            try {
                val data = ioScope.async {
                    return@async ProductsRepository.getProducts(tag)
                }.await()

                products.value = data.toMutableList()
            } catch (e: Exception) {
                products.value = mutableListOf()
            }
        }

        return products
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}