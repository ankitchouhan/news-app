package com.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.newsapp.R
import com.newsapp.utilities.InjectorUtils
import com.newsapp.viewmodels.NewsListingViewModel

class NewsListingActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_listing)

        viewModel = ViewModelProvider(this, InjectorUtils.provideViewModelFactory(this))
            .get(NewsListingViewModel::class.java)
    }
}
