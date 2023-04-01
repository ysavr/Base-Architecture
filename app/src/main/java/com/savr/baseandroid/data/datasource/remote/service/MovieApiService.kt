package com.savr.baseandroid.data.datasource.remote.service

import com.savr.baseandroid.data.datasource.remote.response.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getListMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<MovieDto>
}