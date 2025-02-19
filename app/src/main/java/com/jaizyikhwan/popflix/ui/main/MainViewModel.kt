package com.jaizyikhwan.popflix.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaizyikhwan.core.data.Resource
import com.jaizyikhwan.core.domain.model.Film
import com.jaizyikhwan.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {

    /**
     * StateFlow for popular films
     */
    private val _popularFilms = MutableStateFlow<Resource<List<Film>>>(Resource.Loading())
    val popularFilms: StateFlow<Resource<List<Film>>> = _popularFilms

    /**
     * StateFlow for now playing films
     */
    private val _nowPlayingFilms = MutableStateFlow<Resource<List<Film>>>(Resource.Loading())
    val nowPlayingFilms: StateFlow<Resource<List<Film>>> = _nowPlayingFilms

    /**
     * StateFlow for top rated films
     */
    private val _topRatedFilms = MutableStateFlow<Resource<List<Film>>>(Resource.Loading())
    val topRatedFilms: StateFlow<Resource<List<Film>>> = _topRatedFilms

    /**
     * StateFlow for upcoming films
     */
    private val _upcomingFilms = MutableStateFlow<Resource<List<Film>>>(Resource.Loading())
    val upcomingFilms: StateFlow<Resource<List<Film>>> = _upcomingFilms


    /**
     * StateFlow for search query
     */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    /**
     * StateFlow for search results
     */
    private val _searchResults = MutableStateFlow<List<Film>>(emptyList())
    val searchResults: StateFlow<List<Film>> = _searchResults

    init {
        loadFilms()
        observeSearchResults()
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

            // Load top rated films
            val topRatedFilmsJob = async {
                filmUseCase.getTopRatedFilms().collect {
                    _topRatedFilms.value = it
                }
            }

            // Load upcoming films
            val upcomingFilmsJob = async {
                filmUseCase.getUpcomingFilms().collect {
                    _upcomingFilms.value = it
                }
            }

            popularFilmsJob.await()
            nowPlayingFilmsJob.await()
            topRatedFilmsJob.await()
            upcomingFilmsJob.await()
        }
    }

    /**
     * Function to update search query
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    /**
     * Function to filter films based on search query
     */
    private fun observeSearchResults() {
        viewModelScope.launch {
            combine(_searchQuery, _popularFilms, _nowPlayingFilms, _topRatedFilms, _upcomingFilms) { query, popular, nowPlaying, topRated, upcoming ->
                if (query.isEmpty()) {
                    emptyList()
                } else {
                    val allFilms = mutableListOf<Film>()
                    if (popular is Resource.Success) {
                        popular.data?.let { allFilms.addAll(it) }
                    }
                    if (nowPlaying is Resource.Success) {
                        nowPlaying.data?.let { allFilms.addAll(it) }
                    }
                    if (topRated is Resource.Success) {
                        topRated.data?.let { allFilms.addAll(it) }
                    }
                    if (upcoming is Resource.Success) {
                        upcoming.data?.let { allFilms.addAll(it) }
                    }
                    allFilms.filter { it.title.contains(query, ignoreCase = true) }
                }
            }.collect { results ->
                _searchResults.value = results
            }
        }
    }

}
