package com.newsapp.db

import android.util.Log
import androidx.paging.DataSource
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class ArticleLocalCache(
    private val articleDao: ArticleDao,
    private val ioExecutor: Executor
) {

    /**
     * Insert a list of articles in the database, on a background thread.
     */
    fun insert(articles: List<ArticleEntity>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("LocalCache", "inserting ${articles.size} articles")
            articleDao.insert(articles)
            insertFinished()
        }
    }


    /**
     * Request a LiveData<List<ArticleEntity>> from the Dao.
     */
    fun getArticles(): DataSource.Factory<Int, ArticleEntity> {
        return articleDao.getArticles()
    }
}