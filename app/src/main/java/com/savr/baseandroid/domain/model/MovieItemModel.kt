package com.savr.baseandroid.domain.model

data class MovieItemModel(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val adult: Boolean,
    val backdrop_path: String,
    var isFavorite: Boolean = false
)
