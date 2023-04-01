package com.savr.baseandroid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.baseandroid.common.Resource
import com.savr.baseandroid.domain.usecase.GetDiscoverMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase
) : ViewModel() {

    fun getDiscoverMovie() {
        viewModelScope.launch {
            getDiscoverMovieUseCase().collect { result ->
                when(result){
                    is Resource.Error -> Timber.d(result.message)
                    is Resource.Exception -> Timber.e(result.exception)
                    is Resource.Loading -> Timber.d("Loading")
                    is Resource.Success -> Timber.d("${result.data.size}")
                }
            }
        }
    }
}