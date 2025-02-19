package com.jaizyikhwan.core.data.source.remote

import android.util.Log
import com.jaizyikhwan.core.data.source.remote.network.ApiResponse
import com.jaizyikhwan.core.data.source.remote.network.ApiService
import com.jaizyikhwan.core.data.source.remote.response.FilmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService
) {
    fun getPopularFilms(): Flow<ApiResponse<List<FilmItem>>> {
        return flow {
            try {
                val response = apiService.getPopularFilms()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Error fetching popular films: ${e.message}", e)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getNowPlayingFilms(): Flow<ApiResponse<List<FilmItem>>> {
        return flow {
            try {
                val response = apiService.getNowPlayingFilms()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Error fetching now playing films: ${e.message}", e)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTopRatedFilms(): Flow<ApiResponse<List<FilmItem>>> {
        return flow {
            try {
                val response = apiService.getTopRatedFilms()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Error fetching top rated films: ${e.message}", e)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUpcomingFilms(): Flow<ApiResponse<List<FilmItem>>> {
        return flow {
            try {
                val response = apiService.getUpcomingFilms()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Error fetching upcoming films: ${e.message}", e)
            }
        }.flowOn(Dispatchers.IO)
    }

}