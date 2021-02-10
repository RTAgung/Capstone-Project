package com.example.moviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var movieId: String? = null,
    var originalTitle: String? = null,
    var title: String? = null,
    var originalLanguage: String? = null,
    var overview: String? = null,
    var releaseDate: String? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null,
    var posterPath: String? = null,
    var backdropPath: String? = null
) : Parcelable