package com.newsapp.db

import androidx.paging.DataSource
import androidx.room.*

/**
 * Room data access object for accessing the articles table.
 */

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    fun clearTable()

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun getArticles(): DataSource.Factory<Int, ArticleEntity>

    @Transaction
    fun refreshDb(articles: List<ArticleEntity>) {
        clearTable()
        insert(articles)
    }
}