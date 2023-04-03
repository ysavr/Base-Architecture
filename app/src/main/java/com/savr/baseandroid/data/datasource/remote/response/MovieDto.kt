package com.savr.baseandroid.data.datasource.remote.response

import com.savr.baseandroid.domain.model.MovieItemModel

data class MovieDto(
    val page: Int,
    val results: List<MovieItemDto>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieItemDto(
    val id: Int,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    val adult: Boolean? = null,
    val backdrop_path: String? = null
) {
    fun toModel() = MovieItemModel(
        id,
        original_language ?: "",
        original_title ?: "",
        overview ?: "",
        popularity ?: 0.0,
        poster_path ?: "",
        release_date ?: "",
        title ?: "",
        video ?: false,
        vote_average ?: 0.0,
        vote_count ?: 0,
        adult ?: false,
        backdrop_path ?: ""
    )
}