package com.newsapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "headline") val headline: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "publishTime") val publishTime: String?
)