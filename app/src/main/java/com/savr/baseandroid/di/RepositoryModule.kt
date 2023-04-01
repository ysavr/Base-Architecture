package com.savr.baseandroid.di

import com.savr.baseandroid.common.DispatchersProvider
import com.savr.baseandroid.data.datasource.remote.service.MovieApiService
import com.savr.baseandroid.data.repository.MovieRepositoryImpl
import com.savr.baseandroid.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        service: MovieApiService,
        dispatchersProvider: DispatchersProvider
    ): MovieRepository {
        return MovieRepositoryImpl(service, dispatchersProvider)
    }
}