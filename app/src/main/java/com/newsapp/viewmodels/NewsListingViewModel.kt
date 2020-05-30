package com.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import com.newsapp.data.NewsListingRepository

class NewsListingViewModel(private val repository: NewsListingRepository) : ViewModel() {
}