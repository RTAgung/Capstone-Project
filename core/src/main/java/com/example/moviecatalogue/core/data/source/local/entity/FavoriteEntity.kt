package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "originalTitle") var originalTitle: String? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "originalLanguage") var originalLanguage: String? = null,
    @ColumnInfo(name = "overview") var overview: String? = null,
    @ColumnInfo(name = "releaseDate") var releaseDate: String? = null,
    @ColumnInfo(name = "voteAverage") var voteAverage: Double? = null,
    @ColumnInfo(name = "voteCount") var voteCount: Int? = null,
    @ColumnInfo(name = "posterPath") var posterPath: String? = null,
    @ColumnInfo(name = "backdropPath") var backdropPath: String? = null
)