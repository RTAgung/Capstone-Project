package com.example.moviecatalogue.di

import com.example.moviecatalogue.core.domain.usecase.MovieInteractor
import com.example.moviecatalogue.core.domain.usecase.MovieUseCase
import com.example.moviecatalogue.detail.DetailViewModel
import com.example.moviecatalogue.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}