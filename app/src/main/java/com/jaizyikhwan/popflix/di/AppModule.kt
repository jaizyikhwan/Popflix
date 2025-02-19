package com.jaizyikhwan.popflix.di

import com.jaizyikhwan.core.domain.usecase.FilmInteractor
import com.jaizyikhwan.core.domain.usecase.FilmUseCase
import com.jaizyikhwan.popflix.ui.detail.DetailViewModel
import com.jaizyikhwan.popflix.ui.favorite.FavoriteViewModel
import com.jaizyikhwan.popflix.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    // Modul untuk UseCase
    factory<FilmUseCase> { FilmInteractor(get()) }

    // Modul untuk ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}