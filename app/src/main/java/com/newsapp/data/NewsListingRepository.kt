package com.newsapp.data

import com.newsapp.api.ApiService
import com.newsapp.db.ArticleLocalCache

/**
 * Repository class that works with local and remote data sources.
 */
class NewsListingRepository(
    private val apiService: ApiService,
    private val articleLocalCache: ArticleLocalCache) {

}