package com.example.moviecatalogue.core.domain.usecase

import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovie() = movieRepository.getAllMovie()

    override fun getAllMovie(query: String) = movieRepository.getAllMovie(query)

    override fun getFavorite() = movieRepository.getFavorite()

    override fun checkFavorite(favoriteId: String) = movieRepository.checkFavorite(favoriteId)

    override fun insertFavorite(favorite: Movie) = movieRepository.insertFavorite(favorite)

    override fun deleteFavorite(favorite: Movie) = movieRepository.deleteFavorite(favorite)
}