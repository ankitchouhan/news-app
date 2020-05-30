package com.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.newsapp.api.ApiService
import com.newsapp.api.getTopHeadlines
import com.newsapp.db.ArticleEntity
import com.newsapp.db.ArticleLocalCache

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class NewsBoundaryCallback(
    private val apiService: ApiService,
    private val articleLocalCache: ArticleLocalCache
) : PagedList.BoundaryCallback<ArticleEntity>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = articleLocalCache.getLastFetchedPage()

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    // check if result available on server
    private var totalResultAvailable = 0

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("BoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData(lastRequestedPage)
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: ArticleEntity) {
        Log.d("BoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData(lastRequestedPage)
    }

    fun requestAndSaveData(page: Int) {
        lastRequestedPage = page
        if (isRequestInProgress) return
        isRequestInProgress = true

        getTopHeadlines(
            apiService,
            NETWORK_PAGE_SIZE,
            lastRequestedPage, { articles, totalResults ->
                totalResultAvailable = totalResults
                val list = articles.map { article ->
                    ArticleEntity(
                        url = article.url,
                        headline = article.title,
                        image = article.urlToImage,
                        description = article.description,
                        source = article.source.name,
                        publishTime = article.publishedAt
                    )
                }
                if (lastRequestedPage == 1) {
                    articleLocalCache.clearAndInsert(list) {
                        articleLocalCache.updateLastFetchTime()
                        articleLocalCache.updateLastFetchedPage(lastRequestedPage)
                        lastRequestedPage++
                        isRequestInProgress = false
                    }
                } else {
                    articleLocalCache.insert(list) {
                        articleLocalCache.updateLastFetchedPage(lastRequestedPage)
                        lastRequestedPage++
                        isRequestInProgress = false
                    }
                }
            },
            { error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false

            })
    }
}