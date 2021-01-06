package com.example.moviecatalogue.core.utils

import com.example.moviecatalogue.core.data.source.local.entity.FavoriteEntity
import com.example.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.core.data.source.remote.response.ResultsItem
import com.example.moviecatalogue.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<ResultsItem>): List<MovieEntity> {
        val tourismList = ArrayList<MovieEntity>()
        input.map {
            val tourism = it.id?.let { responseId ->
                MovieEntity(
                    id = responseId,
                    originalTitle = it.originalTitle,
                    title = it.title,
                    backdropPath = it.backdropPath,
                    posterPath = it.posterPath,
                    voteCount = it.voteCount,
                    voteAverage = it.voteAverage,
                    originalLanguage = it.originalLanguage,
                    releaseDate = it.releaseDate,
                    overview = it.overview
                )
            } as MovieEntity
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapFavoriteEntToMovieEnt(input: List<FavoriteEntity>): List<MovieEntity> =
        input.map {
            MovieEntity(
                id = it.id,
                originalTitle = it.originalTitle,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                voteCount = it.voteCount,
                voteAverage = it.voteAverage,
                originalLanguage = it.originalLanguage,
                releaseDate = it.releaseDate,
                overview = it.overview
            )
        }

    fun mapMovieEntToFavoriteEnt(input: MovieEntity): FavoriteEntity =
        FavoriteEntity(
            id = input.id,
            originalTitle = input.originalTitle,
            title = input.title,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            voteCount = input.voteCount,
            voteAverage = input.voteAverage,
            originalLanguage = input.originalLanguage,
            releaseDate = input.releaseDate,
            overview = input.overview
        )


    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.id,
                originalTitle = it.originalTitle,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                voteCount = it.voteCount,
                voteAverage = it.voteAverage,
                originalLanguage = it.originalLanguage,
                releaseDate = it.releaseDate,
                overview = it.overview
            )
        }

    fun mapDomainToEntity(input: Movie) = input.movieId?.let {
        MovieEntity(
            id = it,
            originalTitle = input.originalTitle,
            title = input.title,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            voteCount = input.voteCount,
            voteAverage = input.voteAverage,
            originalLanguage = input.originalLanguage,
            releaseDate = input.releaseDate,
            overview = input.overview
        )
    }
}