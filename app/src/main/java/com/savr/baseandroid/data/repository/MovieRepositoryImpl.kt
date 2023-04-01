package com.savr.baseandroid.data.repository

import com.savr.baseandroid.common.Constant
import com.savr.baseandroid.common.DispatchersProvider
import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.data.datasource.remote.service.MovieApiService
import com.savr.baseandroid.domain.model.MovieItemModel
import com.savr.baseandroid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieApiService,
    private val dispatcher: DispatchersProvider
) : MovieRepository {
    override suspend fun getDiscoverMovie(): Flow<Resource<List<MovieItemModel>>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = service.getListMovie(Constant.API_KEY, Constant.LANGUAGE)
                if (response.isSuccessful) {
                    val listData = response.body()?.results?.map {
                        it.toModel()
                    }
                    emit(Resource.Success(data = listData!!))
                } else {
                    emit(Resource.Error(code = response.code(), message = response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Exception(exception = e))
            }
        }.flowOn(dispatcher.io)
    }
}