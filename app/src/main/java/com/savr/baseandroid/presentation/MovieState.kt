package com.savr.baseandroid.presentation

import com.savr.baseandroid.domain.model.MovieItemModel

sealed class MovieState {
    data class Success(val data: List<MovieItemModel>) : MovieState()
    data class Error(val message: String, val code: Int? = null) : MovieState()
    object Loading : MovieState()
    object Empty : MovieState()
}