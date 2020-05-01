package com.nextshop.service.api

import java.lang.Exception

sealed class BaseResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : BaseResult<T>()
    data class Error(val exception: Exception) : BaseResult<Nothing>()
}