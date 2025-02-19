package com.jaizyikhwan.core.data.source.remote.network

import com.jaizyikhwan.core.data.source.remote.response.FilmResponse
import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularFilms(): FilmResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingFilms(): FilmResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedFilms(): FilmResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingFilms(): FilmResponse

}