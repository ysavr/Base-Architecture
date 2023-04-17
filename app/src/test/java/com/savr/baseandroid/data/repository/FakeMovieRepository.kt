package com.savr.baseandroid.data.repository

import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.model.MovieItemModel
import com.savr.baseandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MovieRepository {
    private val movie = MovieItemModel(
        1,
        "en",
        "tittle one",
        "overview",
        0.0,
        "",
        "",
        "tittle one",
        false,
        0.0,
        0,
        false,
        "",
        false
    )

    private val movie2 = MovieItemModel(
        2,
        "en",
        "tittle two",
        "overview",
        0.0,
        "",
        "",
        "tittle two",
        false,
        0.0,
        0,
        false,
        "",
        false
    )

    private val movie3 = MovieItemModel(
        3,
        "en",
        "tittle three",
        "overview",
        0.0,
        "",
        "",
        "tittle three",
        false,
        0.0,
        0,
        false,
        "",
        false
    )

    private val movies = mutableListOf(movie, movie2, movie3)

    override suspend fun getDiscoverMovie(page: Int): Flow<Resource<List<MovieItemModel>>> {
        return flow {
//            emit(Resource.Loading)
            emit(Resource.Success(movies))
//            emit(Resource.Error(500, "Internal server error"))
//            emit(Resource.Exception(Exception("Invalid arguments")))
        }
    }

}