package com.newsapp.utilities

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.newsapp.api.ApiService
import com.newsapp.api.ClientAuthInterceptor
import com.newsapp.data.NewsListingRepository
import com.newsapp.db.ArticleLocalCache
import com.newsapp.db.NewsDatabase
import com.newsapp.viewmodels.ViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

object InjectorUtils {

    private val okHttpClient = OkHttpClient.Builder()

    /**
     * Create an instance of [ApiService] based on the interceptor.
     * */
    private fun provideApiService(): ApiService {
        okHttpClient.addInterceptor(ClientAuthInterceptor())
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Create an instance of [ArticleLocalCache] based on the database dao.
     * */
    private fun provideArticleCache(context: Context): ArticleLocalCache {
        val database = NewsDatabase.getInstance(context)
        return ArticleLocalCache(database.articlesDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Create an instance of [NewsListingRepository] based on the [ApiService] and
     * a [ArticleLocalCache].
     * */
    private fun provideNewsListingRepository(context: Context): NewsListingRepository {
        return NewsListingRepository(provideApiService(), provideArticleCache(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * viewModel objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideNewsListingRepository(context))
    }

}