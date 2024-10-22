package com.ingresso.challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingresso.challenge.model.MovieModel
import com.ingresso.challenge.model.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>> get() = _movies
    private var isDataFetched = false

    fun fetchMovies() {
        if (isDataFetched) return

        viewModelScope.launch {
            val movieList = repository.getMovies()
            _movies.postValue(movieList)
            isDataFetched = true
        }
    }
}
