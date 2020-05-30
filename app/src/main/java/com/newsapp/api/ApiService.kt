package com.newsapp.api

import android.util.Log
import com.newsapp.models.Article
import com.newsapp.models.NewsListResponse
import com.newsapp.utilities.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "ApiService"

/**
 *
 * Trigger a request to the News API with the following params:
 * @param pageSize number of articles to be returned by the News API per page
 * @param page request page index
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of articles received
 * @param onError function that defines how to handle request failure
 */
fun getTopHeadlines(
    service: ApiService,
    pageSize: Int,
    page: Int,
    onSuccess: (results: List<Article>, totalResults: Int) -> Unit,
    onError: (error: String) -> Unit
) {
    service.getTopHeadlines(pageSize = pageSize, page = page).enqueue(
        object : Callback<NewsListResponse> {
            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                Log.d(TAG, "Fail to get data")
                onError(t.message ?: "Unknown Error!")
            }

            override fun onResponse(
                call: Call<NewsListResponse>,
                response: Response<NewsListResponse>
            ) {
                Log.d(TAG, "Response is :-> $response")
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    val totalResults = response.body()?.totalResults ?: 0
                    onSuccess(articles, totalResults)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown Error!")
                }
            }

        }
    )
}


/**
 * News API communication setup via Retrofit.
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsListResponse>
}