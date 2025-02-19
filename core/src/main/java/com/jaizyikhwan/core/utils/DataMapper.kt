package com.jaizyikhwan.core.utils

import com.jaizyikhwan.core.data.source.local.entity.FilmEntity
import com.jaizyikhwan.core.data.source.remote.response.FilmItem
import com.jaizyikhwan.core.domain.model.Film

object DataMapper {

    fun mapFilmListToEntities(input: List<FilmItem>): List<FilmEntity> {
        return input.map {
            FilmEntity(
                id = it.id,
                title = it.title,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                video = it.video,
                adult = it.adult,
                isFavorite = false,
            )
        }
    }

    fun mapFilmEntitiesToDomain(input: List<FilmEntity>): List<Film> {
        return input.map {
            Film(
                id = it.id,
                title = it.title,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                video = it.video,
                adult = it.adult,
                isFavorite = it.isFavorite,
            )
        }
    }

    fun mapDomainToEntity(input: Film): FilmEntity {
        return FilmEntity(
            id = input.id,
            title = input.title,
            voteCount = input.voteCount,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            overview = input.overview,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            video = input.video,
            adult = input.adult,
            isFavorite = input.isFavorite,
        )
    }

}