package com.newsapp.api

import com.newsapp.models.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * News API communication setup via Retrofit.
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Response<NewsListResponse>
}