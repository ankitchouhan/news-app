package com.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newsapp.data.NewsListingRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: NewsListingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsListingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}