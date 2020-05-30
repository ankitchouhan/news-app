package com.newsapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "articles", primaryKeys = ["url"])
data class ArticleEntity(
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "headline") val headline: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "publishTime") val publishTime: String?
) : Parcelable