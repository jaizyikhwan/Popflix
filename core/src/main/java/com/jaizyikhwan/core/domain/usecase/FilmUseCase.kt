package com.jaizyikhwan.core.domain.usecase

import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmUseCase {
    fun getPopularFilms(): Flow<Resource<List<Film>>>
    fun getNowPlayingFilms(): Flow<Resource<List<Film>>>
    fun getTopRatedFilms(): Flow<Resource<List<Film>>>
    fun getUpcomingFilms(): Flow<Resource<List<Film>>>
    fun getFavoriteFilms(): Flow<List<Film>>
    fun isFilmFavorited(id: Int): Flow<Boolean>
    suspend fun addFavoriteFilm(film: Film)
    suspend fun removeFavoriteFilm(film: Film)
}