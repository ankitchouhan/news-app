package com.newsapp.utilities

import com.newsapp.api.ApiService
import com.newsapp.api.ClientAuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InjectorUtils {

    private val okHttpClient = OkHttpClient.Builder()

    private fun createApiService(): ApiService {
        okHttpClient.addInterceptor(ClientAuthInterceptor())
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create(ApiService::class.java)
    }
}