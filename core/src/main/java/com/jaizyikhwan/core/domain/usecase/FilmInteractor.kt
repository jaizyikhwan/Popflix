package com.jaizyikhwan.core.domain.usecase

import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.core.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class FilmInteractor(private val filmRepository: FilmRepository) : FilmUseCase {

    override fun getPopularFilms(): Flow<Resource<List<Film>>> =
        filmRepository.getPopularFilms()

    override fun getNowPlayingFilms(): Flow<Resource<List<Film>>> =
        filmRepository.getNowPlayingFilms()

    override fun getTopRatedFilms(): Flow<Resource<List<Film>>> =
        filmRepository.getTopRatedFilms()

    override fun getUpcomingFilms(): Flow<Resource<List<Film>>> =
        filmRepository.getUpcomingFilms()

    override fun getFavoriteFilms(): Flow<List<Film>> =
        filmRepository.getFavoriteFilms()

    override fun isFilmFavorited(id: Int): Flow<Boolean> =
        filmRepository.isFilmFavorited(id)

    override suspend fun addFavoriteFilm(film: Film) =
        filmRepository.addFavoriteFilm(film)

    override suspend fun removeFavoriteFilm(film: Film) =
        filmRepository.removeFavoriteFilm(film)

}