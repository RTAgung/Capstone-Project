package com.example.moviecatalogue.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

//    private var movie: LiveData<Resource<List<Movie>>>? = null
//
//    @ExperimentalCoroutinesApi
//    @FlowPreview
//    fun getMovie(): LiveData<Resource<List<Movie>>>? {
//        if (movie == null) loadMovie(null)
//        return movie
//    }
//
//    @FlowPreview
//    @ExperimentalCoroutinesApi
//    fun loadMovie(query: String?) {
//        movie = null
//        if (query == null) {
//            movie = movieUseCase.getAllMovie().asLiveData()
//        } else {
//            setSearchQuery(query)
//            movie = search
//        }
//    }

    val movie = movieUseCase.getAllMovie().asLiveData()

    @ExperimentalCoroutinesApi
    private val queryChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    fun setSearchQuery(search: String) {
        queryChannel.offer(search)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val search = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieUseCase.getAllMovie(it)
        }.asLiveData()
}