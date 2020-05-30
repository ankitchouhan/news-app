package com.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.db.ArticleEntity
import com.newsapp.utilities.KEY_ARTICLE
import com.newsapp.utilities.Utils
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {

    private var article: ArticleEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        article = intent.getParcelableExtra(KEY_ARTICLE)
        setupData()
    }

    private fun setupData() {
        collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )
        article?.let {
            collapsingToolbar.title = it.headline ?: ""
            headline.text = it.headline ?: ""
            description.text = it.description ?: ""
            source.text = it.source ?: ""
            if (it.publishTime != null) publishDate.text = Utils.formatDate(it.publishTime)
            Glide.with(this)
                .asBitmap()
                .thumbnail(0.2f)
                .load(it.image)
                .into(image)
        }
    }
}