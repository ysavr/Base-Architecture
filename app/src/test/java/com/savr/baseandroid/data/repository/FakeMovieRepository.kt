package com.savr.baseandroid.data.repository

import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.model.MovieItemModel
import com.savr.baseandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MovieRepository {

    override suspend fun getDiscoverMovie(page: Int): Flow<Resource<List<MovieItemModel>>> {
        return flow {
            emit(Resource.Loading)
            emit(Resource.Success(listMovie))
//            emit(Resource.Error(500, "Internal server error"))
//            emit(Resource.Exception(Exception("Invalid arguments")))
        }
    }

}