package com.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.newsapp.data.NewsListingRepository
import com.newsapp.db.ArticleEntity

class NewsListingViewModel(repository: NewsListingRepository) : ViewModel() {

    private val newsResult = repository.getNewsHeadlines()
    val articles: LiveData<PagedList<ArticleEntity>> = newsResult.articleData
    val networkError: LiveData<String> = newsResult.networkErrors

}