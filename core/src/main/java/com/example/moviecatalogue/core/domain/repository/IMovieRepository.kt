package com.example.moviecatalogue.core.domain.repository

import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>

    fun getAllMovie(query: String): Flow<Resource<List<Movie>>>

    fun getFavorite(): Flow<List<Movie>>

    fun checkFavorite(favoriteId: String): Flow<Int>

    fun insertFavorite(favorite: Movie)

    fun deleteFavorite(favorite: Movie)
}