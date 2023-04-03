package com.savr.baseandroid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.usecase.GetDiscoverMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase
) : ViewModel() {

    private val _movieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = _movieState

    private var moviePage = 1

    fun resetPagination() {
        moviePage = 1
    }

    fun getDiscoverMovie() {
        viewModelScope.launch {
            getDiscoverMovieUseCase(moviePage).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieState.value =
                            MovieState.Error(result.message ?: "Something went wrong")
                    }
                    is Resource.Exception -> Timber.e(result.exception)
                    is Resource.Loading -> {
                        _movieState.value = MovieState.Loading
                    }
                    is Resource.Success -> {
                        if (result.data.isNotEmpty()) moviePage++
                        _movieState.value = MovieState.Success(result.data)
                    }
                }
            }
        }
    }
}

