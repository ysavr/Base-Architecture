package com.savr.baseandroid.domain.repository

import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.model.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getDiscoverMovie(): Flow<Resource<List<MovieItemModel>>>
}