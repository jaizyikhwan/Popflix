package com.jaizyikhwan.core.data.source.repository

import android.util.Log
import com.jaizyikhwan.core.data.NetworkBoundResource
import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.core.data.source.local.LocalDataSource
import com.jaizyikhwan.core.data.source.remote.RemoteDataSource
import com.jaizyikhwan.core.data.source.remote.network.ApiResponse
import com.jaizyikhwan.core.data.source.remote.response.FilmItem
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.core.domain.repository.FilmRepository
import com.jaizyikhwan.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : FilmRepository {

    override fun getPopularFilms(): Flow<Resource<List<Film>>> =
        object : NetworkBoundResource<List<Film>, List<FilmItem>>() {

            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getPopularFilms().map {
                    DataMapper.mapFilmEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<FilmItem>>> =
                remoteDataSource.getPopularFilms()

            override suspend fun saveCallResult(data: List<FilmItem>) {
                val movieList = DataMapper.mapFilmListToEntities(data).map {
                    it.copy(isPopular = true)
                }
                localDataSource.insertFilms(movieList)
            }
        }.asFlow()

    override fun getNowPlayingFilms(): Flow<Resource<List<Film>>> =
        object : NetworkBoundResource<List<Film>, List<FilmItem>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getNowPlayingFilms().map {
                    DataMapper.mapFilmEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<FilmItem>>> =
                remoteDataSource.getNowPlayingFilms()

            override suspend fun saveCallResult(data: List<FilmItem>) {
                val filmList = DataMapper.mapFilmListToEntities(data).map {
                    it.copy(isNowPlaying = true)
                }
                localDataSource.insertFilms(filmList)
            }
        }.asFlow()

    override fun getTopRatedFilms(): Flow<Resource<List<Film>>> =
        object : NetworkBoundResource<List<Film>, List<FilmItem>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getTopRatedFilms().map {
                    DataMapper.mapFilmEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<FilmItem>>> =
                remoteDataSource.getTopRatedFilms()

            override suspend fun saveCallResult(data: List<FilmItem>) {
                val filmList = DataMapper.mapFilmListToEntities(data).map {
                    it.copy(isTopRated = true)
                }
                localDataSource.insertFilms(filmList)
            }
        }.asFlow()

    override fun getUpcomingFilms(): Flow<Resource<List<Film>>> =
        object : NetworkBoundResource<List<Film>, List<FilmItem>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getUpcomingFilms().map {
                    DataMapper.mapFilmEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<FilmItem>>> =
                remoteDataSource.getUpcomingFilms()

            override suspend fun saveCallResult(data: List<FilmItem>) {
                val filmList = DataMapper.mapFilmListToEntities(data).map {
                    it.copy(isUpcoming = true)
                }
                localDataSource.insertFilms(filmList)
            }
        }.asFlow()


    override fun getFavoriteFilms(): Flow<List<Film>> {
        return localDataSource.getFavoriteFilms().map {
            DataMapper.mapFilmEntitiesToDomain(it)
        }
    }

    override fun isFilmFavorited(id: Int): Flow<Boolean> {
        return localDataSource.isFilmFavorited(id)
    }

    override suspend fun addFavoriteFilm(film: Film) {
        val filmEntity = DataMapper.mapDomainToEntity(film)
        localDataSource.insertFilmIfNotExists(filmEntity)
        localDataSource.updateFavoriteStatus(filmEntity.id, true)
    }

    override suspend fun removeFavoriteFilm(film: Film) {
        val filmEntity = DataMapper.mapDomainToEntity(film)
        localDataSource.insertFilmIfNotExists(filmEntity)
        localDataSource.updateFavoriteStatus(filmEntity.id, false)
    }

}
