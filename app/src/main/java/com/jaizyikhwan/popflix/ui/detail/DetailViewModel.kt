package com.jaizyikhwan.popflix.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    private val _popularFilms = MutableStateFlow<Resource<List<Film>>>(Resource.Loading())
    val popularFilms: StateFlow<Resource<List<Film>>> = _popularFilms

    private val _nowPlayingFilms = MutableStateFlow<Resource<List<Film>>>(Resource.Loading())
    val nowPlayingFilms: StateFlow<Resource<List<Film>>> = _nowPlayingFilms

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        loadFilms()
    }

    /**
     * Function to load popular films and now playing films.
     */
    private fun loadFilms() {
        viewModelScope.launch {
            // Load popular films
            val popularFilmsJob = async {
                filmUseCase.getPopularFilms().collect {
                    _popularFilms.value = it
                }
            }

            // Load now playing films
            val nowPlayingFilmsJob = async {
                filmUseCase.getNowPlayingFilms().collect {
                    _nowPlayingFilms.value = it
                }
            }

            popularFilmsJob.await()
            nowPlayingFilmsJob.await()
        }
    }

    /**
     * Check if a film is favorited
     */
    fun checkFavoriteStatus(id: Int) {
        viewModelScope.launch {
            filmUseCase.isFilmFavorited(id).collect { isFav ->
                _isFavorite.value = isFav
            }
        }
    }

    /**
     * Add a film to favorites
     */
    fun addFavoriteFilm(film: Film) {
        viewModelScope.launch {
            filmUseCase.addFavoriteFilm(film)
            checkFavoriteStatus(film.id)
        }
    }

    /**
     * Remove a film from favorites
     */
    fun removeFavoriteFilm(film: Film) {
        viewModelScope.launch {
            filmUseCase.removeFavoriteFilm(film)
            checkFavoriteStatus(film.id)
        }
    }
}