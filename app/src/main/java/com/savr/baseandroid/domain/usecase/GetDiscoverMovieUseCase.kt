package com.savr.baseandroid.domain.usecase

import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.model.MovieItemModel
import com.savr.baseandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiscoverMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(): Flow<Resource<List<MovieItemModel>>> {
        return repository.getDiscoverMovie()
    }

}