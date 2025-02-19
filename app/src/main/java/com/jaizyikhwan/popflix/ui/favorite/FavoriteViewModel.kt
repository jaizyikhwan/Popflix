package com.jaizyikhwan.popflix.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    private val _favoriteFilms = MutableStateFlow<List<Film>>(emptyList())
    val favoriteFilms: StateFlow<List<Film>> = _favoriteFilms.asStateFlow()

    init {
        fetchFavoriteFilms()
    }

    private fun fetchFavoriteFilms() {
        viewModelScope.launch {
            filmUseCase.getFavoriteFilms().collect { films ->
                _favoriteFilms.value = films
                println("Favorite Films Updated: $films")
            }
        }
    }

}
