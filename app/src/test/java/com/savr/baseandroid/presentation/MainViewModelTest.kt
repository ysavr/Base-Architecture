package com.savr.baseandroid.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.savr.baseandroid.MainDispatcherRule
import com.savr.baseandroid.data.repository.FakeMovieRepository
import com.savr.baseandroid.data.repository.listMovie
import com.savr.baseandroid.domain.usecase.GetDiscoverMovieUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private lateinit var getDiscoverMovieUseCase: GetDiscoverMovieUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        fakeMovieRepository = FakeMovieRepository()
        getDiscoverMovieUseCase = GetDiscoverMovieUseCase(fakeMovieRepository)
        mainViewModel = MainViewModel(getDiscoverMovieUseCase)
    }

    @Test
    fun `get movies`() {
        runBlocking {
            mainViewModel.getDiscoverMovie()
            mainViewModel.movieState.test {
                assertThat(this.expectMostRecentItem()).isEqualTo(MovieState.Success(listMovie))
            }
        }
    }
}