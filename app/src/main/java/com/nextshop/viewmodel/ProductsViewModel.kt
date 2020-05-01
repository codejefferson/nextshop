package com.nextshop.viewmodel

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextshop.mechanism.SimpleCoroutines
import com.nextshop.repository.ProductsRepository
import com.nextshop.service.model.ProductItemResponse

class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var products: MutableLiveData<List<ProductItemResponse>>

    @UiThread
    fun fetchProducts(tag: String): LiveData<List<ProductItemResponse>> {
        if (!::products.isInitialized) {
            this.products = MutableLiveData()

            // Request data
            SimpleCoroutines.ioThenMain({
                ProductsRepository.getProducts(tag)
            }) {
                it?.let { data ->
                    if (data.isNotEmpty()) {
                        products.value = data.toMutableList()
                    }
                }
            }

        }
        return products
    }

}