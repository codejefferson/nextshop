package com.nextshop.viewmodel

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextshop.mechanism.SimpleCoroutines
import com.nextshop.repository.ProductRepository
import com.nextshop.service.model.ProductDetailResponse

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var product: MutableLiveData<ProductDetailResponse>

    @UiThread
    fun fetchProduct(productId: String): LiveData<ProductDetailResponse> {
        if (!::product.isInitialized) {
            this.product = MutableLiveData()

            // Request data
            SimpleCoroutines.ioThenMain({
                ProductRepository.getProduct(productId)
            }) {
                it?.let { data ->
                    product.value = data.product
                }
            }

        }
        return product
    }

}