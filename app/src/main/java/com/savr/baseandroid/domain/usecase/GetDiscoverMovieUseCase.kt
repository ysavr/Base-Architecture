package com.savr.baseandroid.domain.usecase

import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.model.MovieItemModel
import com.savr.baseandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDiscoverMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
) {

    private var movies: MutableList<MovieItemModel>? = null

    suspend operator fun invoke(page: Int): Flow<Resource<List<MovieItemModel>>> {
        return flow {
            repository.getDiscoverMovie(page).collect {
                when (it) {
                    is Resource.Error -> emit(Resource.Error(code = it.code, message = it.message))
                    is Resource.Exception -> emit(Resource.Exception(it.exception))
                    is Resource.Loading -> emit(Resource.Loading)
                    is Resource.Success -> {
                        if (movies == null) {
                            movies = it.data.toMutableList()
                        } else {
                            it.data.forEach { movie ->
                                movies?.add(movie)
                            }
                        }
                        emit(Resource.Success(movies ?: it.data))
                    }
                }
            }
        }
    }

}