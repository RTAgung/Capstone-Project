package com.example.moviecatalogue.core.data.source.local

import com.example.moviecatalogue.core.data.source.local.entity.FavoriteEntity
import com.example.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun getFavorite(): Flow<List<FavoriteEntity>> = movieDao.getFavorite()

    fun checkFavorite(favoriteId: String): Flow<Int> = movieDao.checkFavorite(favoriteId)

    fun insertFavorite(favorite: FavoriteEntity) = movieDao.insertFavorite(favorite)

    fun deleteFavorite(favorite: FavoriteEntity) = movieDao.deleteFavorite(favorite)
}