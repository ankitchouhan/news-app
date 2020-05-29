package com.newsapp.models


import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("articles")
    val articles: List<Article> = emptyList(),
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int = 0
)