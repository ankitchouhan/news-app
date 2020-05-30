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
import com.newsapp.utilities.PREF_UPDATED_AT
import com.newsapp.utilities.Utils
import com.newsapp.viewmodels.NewsListingViewModel
import kotlinx.android.synthetic.main.activity_news_listing.*
import java.util.*
import kotlin.time.ExperimentalTime

class NewsListingActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListingViewModel
    private val adapter = NewsListingAdapter()

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_listing)

        //getting the view model.
        viewModel = ViewModelProvider(this, InjectorUtils.provideViewModelFactory(this))
            .get(NewsListingViewModel::class.java)

        list.adapter = adapter
        progressBar.visibility = View.VISIBLE
        readLastFetchTime()
        initObservers()
    }

    @ExperimentalTime
    private fun readLastFetchTime() {
        val sharedPref = InjectorUtils.provideSharedPreference(this)
        val lastFetchedTime = sharedPref.getDataFromSharedPref(PREF_UPDATED_AT, 0L)
        if (lastFetchedTime < 1) return
        val hoursDifference =
            Utils.getDateDiff(lastFetchedTime, Calendar.getInstance().timeInMillis)
        if (hoursDifference >= 2) {
            viewModel.refreshHeadlines()
        }
    }

    private fun initObservers() {
        viewModel.articles.observe(this, Observer {
            Log.d("Activity", "list: ${it?.size}")
            progressBar.visibility = View.GONE
            adapter.submitList(it)
            showEmptyText(it?.size == 0)
        })

        viewModel.networkError?.observe(this, Observer {
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
