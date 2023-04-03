package com.savr.baseandroid.domain.repository

import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.model.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getDiscoverMovie(page: Int): Flow<Resource<List<MovieItemModel>>>
}