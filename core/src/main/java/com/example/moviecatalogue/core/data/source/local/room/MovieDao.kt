package com.example.moviecatalogue.core.data.source.local.room

import androidx.room.*
import com.example.moviecatalogue.core.data.source.local.entity.FavoriteEntity
import com.example.moviecatalogue.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Query("SELECT * FROM favorite")
    fun getFavorite(): Flow<List<FavoriteEntity>>

    @Query("SELECT 1 FROM favorite WHERE id = :favoriteId")
    fun checkFavorite(favoriteId: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)
}