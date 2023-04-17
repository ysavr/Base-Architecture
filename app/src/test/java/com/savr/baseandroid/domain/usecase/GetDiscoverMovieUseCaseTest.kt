package com.savr.baseandroid.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.data.repository.FakeMovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDiscoverMovieUseCaseTest {
    private lateinit var getDiscoverMovieUseCase: GetDiscoverMovieUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository

    @Before
    fun setup() {
        fakeMovieRepository = FakeMovieRepository()
        getDiscoverMovieUseCase = GetDiscoverMovieUseCase(fakeMovieRepository)
    }

    @Test
    fun `get movies loading result`() = runBlocking {
        val getMovie = getDiscoverMovieUseCase(1).first()
        assertThat(getMovie is Resource.Loading).isTrue()
    }

    @Test
    fun `get movies success result`() = runBlocking {
        val getMovie = getDiscoverMovieUseCase(1).first()
        assertThat(getMovie is Resource.Success).isTrue()

        if (getMovie is Resource.Success) {
            assertThat(getMovie.data.size == 3).isTrue()
            assertThat(getMovie.data[0].title == "tittle one").isTrue()
        }
    }

    @Test
    fun `get movies error result`() = runBlocking {
        val getMovie = getDiscoverMovieUseCase(1).first()

        if (getMovie is Resource.Error) {
            assertThat(getMovie.code == 500).isTrue()
            assertThat(getMovie.message == "Internal server error").isTrue()
        }
    }

    @Test
    fun `get movies error exception`() = runBlocking {
        val getMovie = getDiscoverMovieUseCase(1).first()

        if (getMovie is Resource.Exception) {
            assertThat(getMovie.exception.message == "Invalid arguments").isTrue()
        }
    }
}