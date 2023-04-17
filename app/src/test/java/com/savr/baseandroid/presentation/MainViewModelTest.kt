package com.savr.baseandroid.presentation

import com.google.common.truth.Truth.assertThat
import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.data.repository.FakeMovieRepository
import com.savr.baseandroid.domain.usecase.GetDiscoverMovieUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var getDiscoverMovieUseCase: GetDiscoverMovieUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        fakeMovieRepository = FakeMovieRepository()
        getDiscoverMovieUseCase = GetDiscoverMovieUseCase(fakeMovieRepository)
        mainViewModel = MainViewModel(getDiscoverMovieUseCase)
    }

    @Test
    fun `get movies`() {
        runBlocking {
            val getMovie = getDiscoverMovieUseCase(1).first()
            assertThat(getMovie is Resource.Success).isTrue()
        }
    }
}