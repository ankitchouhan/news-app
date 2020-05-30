package com.newsapp.data

import androidx.paging.LivePagedListBuilder
import com.newsapp.api.ApiService
import com.newsapp.db.ArticleLocalCache
import com.newsapp.models.NewsResult

/**
 * Repository class that works with local and remote data sources.
 */
class NewsListingRepository(
    private val apiService: ApiService,
    private val articleLocalCache: ArticleLocalCache
) {

    private var boundaryCallback: NewsBoundaryCallback? = null

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }

    /**
     * Get news headlines.
     * */
    fun getNewsHeadlines(): NewsResult {

        // Get data source factory from the local cache
        val dataSourceFactory = articleLocalCache.getArticles()

        // create a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        boundaryCallback = NewsBoundaryCallback(apiService, articleLocalCache)
        val networkErrors = boundaryCallback?.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return NewsResult(data, networkErrors)
    }

    /**
     * Refresh news headlines.
     * */
    fun refresh() {
        boundaryCallback?.requestAndSaveData(1)
    }
}