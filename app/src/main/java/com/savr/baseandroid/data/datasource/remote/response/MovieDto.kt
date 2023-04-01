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
    val backdrop_path: String
) {
    fun toModel() = MovieItemModel(
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count,
        adult,
        backdrop_path
    )
}