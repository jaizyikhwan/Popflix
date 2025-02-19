package com.jaizyikhwan.core.data.source.local

import com.jaizyikhwan.core.data.source.local.entity.FilmEntity
import com.jaizyikhwan.core.data.source.local.room.FilmDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LocalDataSource(
    private val filmDao: FilmDao
) {

    fun getPopularFilms(): Flow<List<FilmEntity>> = filmDao.getPopularFilms()

    fun getFavoriteFilms(): Flow<List<FilmEntity>> = filmDao.getFavoriteFilms()

    fun getNowPlayingFilms(): Flow<List<FilmEntity>> = filmDao.getNowPlayingFilms()

    fun getTopRatedFilms(): Flow<List<FilmEntity>> = filmDao.getTopRatedFilms()

    fun getUpcomingFilms(): Flow<List<FilmEntity>> = filmDao.getUpcomingFilms()

    suspend fun insertFilms(films: List<FilmEntity>) {
        filmDao.insertFilms(films)
    }

    fun isFilmFavorited(id: Int): Flow<Boolean> = filmDao.isFilmFavorited(id)


    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        filmDao.updateFavoriteStatus(id, isFavorite)
    }

    suspend fun insertFilmIfNotExists(film: FilmEntity) {
        val existingFilm = filmDao.getFilmById(film.id).firstOrNull()
        if (existingFilm == null) {
            filmDao.insertFilms(listOf(film))
        }
    }

}