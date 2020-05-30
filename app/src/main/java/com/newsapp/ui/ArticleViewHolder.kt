package com.newsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.db.ArticleEntity
import com.newsapp.utilities.Utils

/**
 * View Holder for a RecyclerView list item.
 */
class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val headline: TextView = itemView.findViewById(R.id.headline)
    private val source: TextView = itemView.findViewById(R.id.source)
    private val publishDate: TextView = itemView.findViewById(R.id.publishDate)

    companion object {
        fun create(parentView: ViewGroup): ArticleViewHolder {
            val view = LayoutInflater.from(parentView.context)
                .inflate(R.layout.row_list_item, parentView, false)
            return ArticleViewHolder(view)
        }
    }

    init {
        itemView.setOnClickListener {

        }
    }

    fun bind(article: ArticleEntity) {
        showData(article)
    }

    private fun showData(article: ArticleEntity) {
        headline.text = article.headline ?: ""
        source.text = article.source ?: ""
        if (article.publishTime != null)
            publishDate.text = Utils.formatDate(article.publishTime)
        Glide.with(itemView.context)
            .asBitmap()
            .thumbnail(0.1f)
            .load(article.image)
            .into(image)
    }
}