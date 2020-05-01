package com.nextshop.repository

import android.util.Log
import com.nextshop.service.api.BaseResult
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val baseResult : BaseResult<T> = safeApiResult(call, errorMessage)
        var data : T? = null

        when(baseResult) {
            is BaseResult.Success ->
                data = baseResult.data
            is BaseResult.Error -> {
                Log.e("BaseRepository", "$errorMessage & Exception - ${baseResult.exception}")
            }
        }

        return data

    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : BaseResult<T> {
        val response = call.invoke()
        if(response.isSuccessful) return BaseResult.Success(response.body()!!)

        return BaseResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage - Error code from response ${response.raw().code()}"))
    }

}