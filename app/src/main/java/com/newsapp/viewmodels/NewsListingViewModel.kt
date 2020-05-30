package com.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.newsapp.data.NewsListingRepository
import com.newsapp.db.ArticleEntity

class NewsListingViewModel(private val repository: NewsListingRepository) : ViewModel() {

    private val newsResult = repository.getNewsHeadlines()
    val articles: LiveData<PagedList<ArticleEntity>> = newsResult.articleData
    val networkError: LiveData<String>? = newsResult.networkErrors

    fun refreshHeadlines() {
        repository.refresh()
    }
}