package com.example.moviecatalogue.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    var movie: Movie = Movie()

    fun checkFavorite() = movie.movieId?.let { movieUseCase.checkFavorite(it).asLiveData() }

    fun insertFavorite() = movieUseCase.insertFavorite(movie)

    fun deleteFavorite() = movieUseCase.deleteFavorite(movie)
}