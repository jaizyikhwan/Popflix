package com.jaizyikhwan.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaizyikhwan.core.data.source.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * FROM films")
    fun getAllFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE id = :filmId")
    fun getFilmById(filmId: Int): Flow<FilmEntity>

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    fun getFavoriteFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE isPopular = 1")
    fun getPopularFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE isNowPlaying = 1")
    fun getNowPlayingFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE isTopRated = 1")
    fun getTopRatedFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE isUpcoming = 1")
    fun getUpcomingFilms(): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(film: List<FilmEntity>)

    @Delete
    suspend fun deleteFavoriteFilm(film: FilmEntity)

    @Query("DELETE FROM films WHERE isFavorite = 1")
    suspend fun deleteAllFavoriteFilms()

    @Query("SELECT isFavorite FROM films WHERE id = :id")
    fun isFilmFavorited(id: Int): Flow<Boolean>

    @Query("UPDATE films SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}