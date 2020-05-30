package com.newsapp.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.newsapp.db.ArticleEntity

data class NewsResult(
    val articleData: LiveData<PagedList<ArticleEntity>>,
    val networkErrors: LiveData<String>?
)