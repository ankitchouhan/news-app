package com.newsapp.db

import android.util.Log
import androidx.paging.DataSource
import com.newsapp.utilities.PREF_LAST_FETCHED_PAGE
import com.newsapp.utilities.PREF_UPDATED_AT
import java.util.*
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class ArticleLocalCache(
    private val articleDao: ArticleDao,
    private val sharedPreference: AppSharedPreference,
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
     * Clear and Insert a list of articles in the database, on a background thread.
     */
    fun clearAndInsert(articles: List<ArticleEntity>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("LocalCache", "clearing and inserting ${articles.size} articles")
            articleDao.refreshDb(articles)
            insertFinished()
        }
    }

    /**
     * Update last data fetch time in the preferences.
     */
    fun updateLastFetchTime() {
        sharedPreference.addToSharedPref(PREF_UPDATED_AT, Calendar.getInstance().timeInMillis)
    }

    fun updateLastFetchedPage(page: Int) {
        sharedPreference.addToSharedPref(PREF_LAST_FETCHED_PAGE, page)
    }

    fun getLastFetchedPage(): Int {
        return sharedPreference.getDataFromSharedPref(PREF_LAST_FETCHED_PAGE, 1)
    }


    /**
     * Request a LiveData<List<ArticleEntity>> from the Dao.
     */
    fun getArticles(): DataSource.Factory<Int, ArticleEntity> {
        return articleDao.getArticles()
    }
}