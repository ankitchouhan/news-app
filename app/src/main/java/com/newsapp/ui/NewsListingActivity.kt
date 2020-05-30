package com.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.newsapp.R
import com.newsapp.utilities.InjectorUtils
import com.newsapp.viewmodels.NewsListingViewModel
import kotlinx.android.synthetic.main.activity_news_listing.*

class NewsListingActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListingViewModel
    private val adapter = NewsListingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_listing)

        //getting the view model.
        viewModel = ViewModelProvider(this, InjectorUtils.provideViewModelFactory(this))
            .get(NewsListingViewModel::class.java)

        list.adapter = adapter
        progressBar.visibility = View.VISIBLE
        initObservers()
    }

    private fun initObservers() {
        viewModel.articles.observe(this, Observer {
            Log.d("Activity", "list: ${it?.size}")
            progressBar.visibility = View.GONE
            showEmptyText(it?.size == 0)
            adapter.submitList(it)
        })

        viewModel.networkError.observe(this, Observer {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }

    private fun showEmptyText(show: Boolean) {
        if (show) {
            list.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        } else {
            list.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
        }
    }
}
