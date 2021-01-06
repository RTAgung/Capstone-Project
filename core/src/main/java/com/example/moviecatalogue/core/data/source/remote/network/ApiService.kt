package com.example.moviecatalogue.core.data.source.remote.network

import com.example.moviecatalogue.core.BuildConfig.API_KEY
import com.example.moviecatalogue.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/now_playing?api_key=$API_KEY")
    suspend fun getAllMovie(@Query("page") page: Int = 1): MovieResponse

    @GET("/3/search/movie?api_key=$API_KEY")
    suspend fun getAllMovie(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieResponse
}