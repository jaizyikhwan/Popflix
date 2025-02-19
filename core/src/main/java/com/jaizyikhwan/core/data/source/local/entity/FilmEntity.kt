package com.jaizyikhwan.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val originalLanguage: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val video: Boolean,
    val adult: Boolean,
    val isFavorite: Boolean = false,
    val isPopular: Boolean = false,
    val isNowPlaying: Boolean = false,
    val isTopRated: Boolean = false,
    val isUpcoming: Boolean = false
)
