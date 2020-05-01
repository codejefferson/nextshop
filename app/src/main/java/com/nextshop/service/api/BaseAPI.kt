package com.nextshop.service.api

import com.nextshop.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseAPI {

    var httpClient = OkHttpClient.Builder()

    private var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())

    private var retrofit: Retrofit = builder
        .client(httpClient.build())
        .build()

    var baseService: BaseService = retrofit.create(
        BaseService::class.java)

}